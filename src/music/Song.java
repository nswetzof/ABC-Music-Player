package music;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import abc.sound.SequencePlayer;

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

/** Represents all the header and body information necessary to create a MIDI song. **/ 
public class Song {
	private Music music;
	SequencePlayer player; // TODO: confirm we need
	
	// header information
	private int index;
	private String title;
	private String composer;
	private double length;
	private Pair<Integer, Integer> meter;
	private Pair<Double, Integer> tempo;
	private String key;
	
	/*
	 * Rep invariant:
	 * 	all fields are non-null;
	 * 	key must be in the set of major and minor keys in western musical notation
	 * Abstraction function:
	 * 	Represents a song with notes defined in 'music' and header information obtained from an abc file
	 * 		defined in the remaining fields.
	 * Safety from rep exposure:
	 * 	all fields are private;
	 * 	meter is mutable so defensive copying is utilized to avoid exposure to clients;
	 * 	music is mutable so getMusic utilizes defensive copying to avoid exposure to clients;
	 * 	the constructor mutates music but that information is not taken directly from
	 * 		any parameters so it is not exposed;
	 */
	
	public Song() {
		this.music = new Rest(0, 1); // TODO: confirm
		
		this.index = 0;
		this.title = "";
		this.composer = "Unknown";
		this.meter = new Pair<Integer, Integer>(4, 4);
		this.length = 1/8;
		this.tempo = new Pair<Double, Integer>(this.length, 100);
		this.key = "";
	}
	
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
	 * Set index of Song
	 * @param i number representing desired index
	 */
	public void setIndex(int i) {
		this.index = i;
		
		checkRep();
	}
	
	/**
	 * Set title of Song
	 * @param t String representing desired title
	 */
	public void setTitle(String t) {
		this.title = t;
		
		checkRep();
	}
	
	/**
	 * Set composer of Song
	 * @param c String representing desired composer
	 */
	public void setComposer(String c) {
		this.composer = c;
		
		checkRep();
	}
	
	/**
	 * Set default note length
	 * @param l desired note length
	 */
	public void setLength(double l) {
		this.length = l;
		
		checkRep();
	}
	
	/**
	 * Set meter of Song
	 * @param num number of beats per measure
	 * @param denom inverse of number of notes per beat (eg. denom of 4 represents 1/4 note per beat)
	 */
	public void setMeter(int num, int denom) {		
		this.meter.setFirst(num);
		this.meter.setSecond(denom);
		
		checkRep();
	}
	
	/**
	 * Set tempo of Song
	 * @param length beat length used when defining number of beats per minute
	 * @param beatsPerMinute how many beats play each minute in the Song
	 */
	public void setTempo(double length, int beatsPerMinute) {
		this.tempo.setFirst(length);
		this.tempo.setSecond(beatsPerMinute);
		
		checkRep();
	}
	
	/**
	 * Set key of Song
	 * @param c String representing desired key
	 * @requires k is a member of the set of major and minor keys in western musical notation 
	 */
	public void setKey(String k) {
		this.key = k;
		
		checkRep();
	}
	
	/** Print header information of song
	 */
	public void printHeader() {
		
	}
	
	/**
	 * Parses an abc file and converts it to a Music object
	 * @param file name and path of abc file for the parser to interpret
	 * @return Music object with properties and musical components defined in file
	 * @throws IOException if error opening file
	 */
	public static Song parse(String file) throws IOException {
		
		throw new RuntimeException("not implemented");
	}
	
	private void checkRep() {
		assert(music != null);
		assert(title != null);
		assert(composer != null);
		assert(meter != null);
		assert(tempo != null);
		assert(key != null);
	}
} // end class Song
