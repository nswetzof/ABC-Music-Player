package music;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import abc.parser.MakeSong;
import abc.parser.MusicLexer;
import abc.parser.MusicParser;
import abc.sound.SequencePlayer;

/** Represents a generic pair of values **/
class Pair<T, U> {
	private T First;
	private U Second;
	
	public Pair(T f, U s) {
		this.setFirst(f);
		this.setSecond(s);
	}
	
	public T first() {
		return First;
	}
	
	public U second() {
		return Second;
	}
	
	public void setFirst(T first) {
		this.First = first;
	}
	
	public void setSecond(U second) {
		this.Second = second;
	}
}

/** Represents all the header and body information necessary to create a MIDI song.
 * 	The composer is labelled as 'Unknown' unless otherwise specified. **/ 
public class Song {
	private Map<String, Music> voices;
	
	// header information
	private int index;
	private String title;
	private String composer;
	private double length;
	private Pair<Integer, Integer> meter;
	private Pair<Double, Integer> tempo;
	private String key;
	
	private final Map<String, MajorKeys> keyMap = new HashMap<String, MajorKeys>(); // maps key String to MajorKeys value
	private final List<Integer> keyOffset;
	
	private static enum MajorKeys {C, G, D, A, E, B, Fs, F, Bb, Eb, Ab, Db};
	private static enum MinorKeys {A, E, B, Fs, Cs, Gs, Ds, D, G, C, F, Bb};
	
	// list of pitch offsets for all key signatures. Each element in the list is a list of the offset amounts for each
	// note where element[i] corresponds with [A, B, C, D, E, F, G][i]
	private static final List<List<Integer>> PITCH_OFFSETS = Arrays.asList(
			Arrays.asList(0, 0, 0, 0, 0, 0, 0), // C
			Arrays.asList(0, 0, 0, 0, 0, 1, 0), // G
			Arrays.asList(0, 0, 1, 0, 0, 1, 0), // D
			Arrays.asList(0, 0, 1, 0, 0, 1, 1), // A
			Arrays.asList(0, 0, 1, 1, 0, 1, 1), // E
			Arrays.asList(1, 0, 1, 1, 0, 1, 1), // B or Cb
			Arrays.asList(1, 0, 1, 1, 1, 1, 1), // F# or Gb
			Arrays.asList(0, -1, 0, 0, 0, 0, 0), // F
			Arrays.asList(0, -1, 0, 0, -1, 0, 0), // Bb
			Arrays.asList(-1, -1, 0, 0, -1, 0, 0), // Eb
			Arrays.asList(-1, -1, 0, -1, -1, 0, 0), // Ab
			Arrays.asList(-1, -1, 0, -1, -1, 0, -1) // Db or C#
			);
	
	// body information
	private int ticksPerBeat;
	
	/*
	 * Rep invariant:
	 * 	all fields are non-null;
	 * 	key must be in the set of major and minor keys in western musical notation
	 * 	voices.size() > 0
	 * 	ticksPerBeat > 0
	 * Abstraction function:
	 * 	Represents a song with a certain number of voices represented by the 'voices' map where keys represent voice
	 * 		names and values represent the notes associated with those voices. Information which would be displayed
	 * 		in the header of an abc file is represented in the remaining fields.
	 * Safety from rep exposure:
	 * 	all fields are private;
	 * 	meter is mutable so defensive copying is utilized to avoid exposure to clients;
	 * 	the values of voices are mutable but are never returned or copied by any method;
	 * 	addVoice only passes a key for voices as a parameter, which are immutable
	 * 
	 * TODO: might be able to have play method which concurrently calls the play methods for each Music object in voices;
	 * 		 would have to figure out what needs to be atomic
	 */
	
	public Song() {
		this.voices = new HashMap<String, Music>();
		
		this.index = 0;
		this.title = "";
		this.composer = "Unknown";
		this.meter = new Pair<Integer, Integer>(4, 4);
		this.length = 1.0/8;
		this.tempo = new Pair<Double, Integer>(this.length, 100);
		this.key = "";
		
		this.ticksPerBeat = 1;
		
		this.initializeKeyMap();
		
		if(this.key.endsWith("m"))
			this.keyOffset = Song.PITCH_OFFSETS.get(
					MajorKeys.valueOf(this.key.substring(0, this.key.length() - 1)).ordinal());
		else
			this.keyOffset = Song.PITCH_OFFSETS.get(this.keyMap.get(this.key).ordinal());
	}
	
	// Observer methods
	
	/**
	 * @return index number of Song
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * @return title of Song
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @return composer of Song
	 */
	public String getComposer() {
		return composer;
	}
	
	/**
	 * @return default note length
	 */
	public double getLength() {
		return length;
	}
	
	/**
	 * @return meter of Song
	 */
	public Pair<Integer, Integer> getMeter() {
		return new Pair<Integer, Integer>(this.meter.first(), this.meter.second());
	}
	
	/**
	 * @return meter expressed as a double value
	 */
	public double getMeterAsValue() {
		return (double)(this.meter.first()) / (double)(this.meter.second());
	}
	
	/**
	 * @return tempo of Song
	 */
	public Pair<Double, Integer> getTempo() {
		return new Pair<Double, Integer>(this.tempo.first(), this.tempo.second());
	}
	
	/**
	 * @return key signature of Song
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * @return the set of names of all voices present in the Song
	 */
	public Set<String> getVoiceNames() {
		return voices.keySet();
	}
	
	/**
	 * @return number of ticks per beat
	 */
	public int getTicksPerBeat() {
		return this.ticksPerBeat;
	}
	
	// Mutator methods
	
	/**
	 * Set index of Song
	 * @param i number representing desired index
	 */
	public void setIndex(int i) {
		this.index = i;
	}
	
	/**
	 * Set title of Song
	 * @param t String representing desired title
	 */
	public void setTitle(String t) {
		this.title = t;
	}
	
	/**
	 * Set composer of Song
	 * @param c String representing desired composer
	 */
	public void setComposer(String c) {
		this.composer = c;
	}
	
	/**
	 * Set default note length
	 * @param l desired note length
	 */
	public void setLength(double l) {
		this.length = l;
	}
	
	/**
	 * Set meter of Song
	 * @param num number of beats per measure
	 * @param denom inverse of number of notes per beat (eg. denom of 4 represents 1/4 note per beat)
	 */
	public void setMeter(int num, int denom) {		
		this.meter.setFirst(num);
		this.meter.setSecond(denom);
	}
	
	/**
	 * Set tempo of Song
	 * @param length beat length used when defining number of beats per minute
	 * @param beatsPerMinute how many beats play each minute in the Song
	 */
	public void setTempo(double length, int beatsPerMinute) {
		this.tempo.setFirst(length);
		this.tempo.setSecond(beatsPerMinute);
	}
	
	/**
	 * Set key of Song
	 * @param c String representing desired key
	 * @requires k is a member of the set of major and minor keys in western musical notation 
	 */
	public void setKey(String k) {
		this.key = k;
	}
	
	/**
	 * Set number of ticks per beat that will be passed into a SequencePlayer object to play the music.
	 * @param perBeat desired number of ticks per beat
	 */
	public void setTicksPerBeat(int perBeat) {
		this.ticksPerBeat = perBeat;
	}
	
	/**
	 * Add a Music object associated with a named voice
	 * @param voiceName represents the name associated with the voice
	 * @return false if Music object is already present, true otherwise
	 */
	public boolean addVoice(String voiceName) {
		if(voices.containsKey(voiceName))
			return false;
		
		voices.put(voiceName, new Rest(0, 1));
		
		checkRep();
		
		return true;
	}
	
	/** Print header information of song
	 */
	public void printHeader() {
		System.out.println("Index Number: " + this.getIndex());
		System.out.println("Title: " + this.getTitle());
		System.out.println("Composer: " + this.getComposer());
		System.out.println("Note Length: " + this.getLength());
		System.out.println("Meter: " + this.getMeter().first() + "/" + this.getMeter().second());
		System.out.println("Tempo: " + this.getTempo().first() + "=" + this.getTempo().second());
		System.out.println("Key: " + this.getKey());
	}
	
	/** 
	 * Add musical element to the song
	 * @param voice name of voice which will play the musical element
	 */
	public boolean addElement(String voiceName, Music element) {
		if(!voices.containsKey(voiceName))
			return false;
		
		voices.put(voiceName, new Concat(voices.get(voiceName), element));
		
		checkRep();
		
		return true;
	}
	
	/**
	 * Play the song represented by this object
	 */
	public void play() {
		// TODO: will create SequencePlayer object here
		
		// Update all Music objects and their children recursively to define uniform number of ticks per beat
		//	for SequencePlayer object
		for(Music m : voices.values())
			m.setTicksPerBeat(this.ticksPerBeat);
	}
	
	/** Set up mappings of key signature strings to MajorKeys enum which corresponds to indices in list of
	 * 	pitch offsets (PITCH_OFFSETS field)
	 */
	private void initializeKeyMap() { // TODO: MAY WANT TO PUT ALL THIS IN MAKESONG BECAUSE KEY SIGNATURES CAN BE
									  //		IN THE BODY OF THE SONG
									  //		OR:
									  //		MAYBE CAN HAVE A FUNCTION 'changeKeySignature' to change within Song
		keyMap.put("A", MajorKeys.A);
		keyMap.put("Ab", MajorKeys.Ab);
		keyMap.put("A#", MajorKeys.Bb);
		
		keyMap.put("B", MajorKeys.B);
		keyMap.put("B#", MajorKeys.C);
		keyMap.put("Bb", MajorKeys.Bb);
		
		keyMap.put("C", MajorKeys.C);
		keyMap.put("C#", MajorKeys.Db);
		keyMap.put("Cb", MajorKeys.B);
		
		keyMap.put("D", MajorKeys.D);
		keyMap.put("D#", MajorKeys.Eb);
		keyMap.put("Db", MajorKeys.Db);
		
		keyMap.put("E", MajorKeys.E);
		keyMap.put("E#", MajorKeys.F);
		keyMap.put("Eb", MajorKeys.Eb);
		
		keyMap.put("F", MajorKeys.F);
		keyMap.put("F#", MajorKeys.Fs);
		keyMap.put("Fb", MajorKeys.E);
		
		keyMap.put("G", MajorKeys.G);
		keyMap.put("G#", MajorKeys.Ab);
		keyMap.put("Gb", MajorKeys.Fs);
	}
	
	/**
	 * Parses an abc file and converts it to a Music object
	 * @param file name and path of abc file for the parser to interpret
	 * @return Music object with properties and musical components defined in file
	 * @throws IOException if error opening file
	 */
	public static Song parse(String file) throws IOException {
		try {
	    	CharStream stream = new ANTLRInputStream(new FileInputStream(file));
	    	
	    	MusicLexer lexer = new MusicLexer(stream);
	    	TokenStream tokens = new CommonTokenStream(lexer);
	    	
	    	MusicParser parser = new MusicParser(tokens);
	    	ParseTree tree = parser.root();
	    	
	    	MakeSong songMaker = new MakeSong();
	    	new ParseTreeWalker().walk(songMaker, tree);
	    	
	    	return songMaker.getSong();
	    	
    	} catch(FileNotFoundException fnfe) {
    		System.err.println(fnfe.getMessage());
    		System.exit(0);
    	} catch(IOException ioe) {
    		System.err.println(ioe.getMessage());
    		System.exit(0);
    	}
		
		return null;
	}
	
	private void checkRep() {
		assert(voices.size() > 0);
		assert(title != null);
		assert(composer != null);
		assert(meter != null);
		assert(tempo != null);
		assert(key != null);
	}
} // end class Song
