package abc.parser;

import java.util.*;

import abc.sound.Pitch;
import music.*;
//import music.Song.MajorKeys;


public class MakeSong extends MusicBaseListener {
	private Map<String, Music> voices = new HashMap<String, Music>();
	
	private Stack<Music> stack = new Stack<Music>();
	private Stack<Pitch> pitchStack = new Stack<Pitch>();
	
	private boolean setLength = false;
	private boolean setMeter = false;
	private boolean setTempo = false;
	
	private final Map<String, String> keyMap = generateKeyMap(); // maps key String to MajorKeys value
	private List<Integer> keyOffset; // store the pitch offsets for each note based on accidentals 
									 //	based on the 'key' field
	
	// store pitch offsets to be applied to just the barline with keys
	// represented by the pitch and values represented by the pitch offsets
	private Map<String, Integer> barlineOffset = new HashMap<String, Integer>(); 
	
	private static enum MajorKeys {C, G, D, A, E, B, Fs, F, Bb, Eb, Ab, Db};
	private static enum MinorKeys {A, E, B, Fs, Db, Ab, Eb, D, G, C, F, Bb};
	
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
	
	Song song = new Song();
	private String currentVoice = "";
	
	/**
	 * @return a Song object with header and music information that can be fed to a SequencePlayer object.
	 */
	public Song getSong() {
		return song;
	}
	
	  /**
	   * Set default values for optional header fields Meter, Length, and Tempo and define default voice if no other
	   * voices are specified
	   */
	  @Override public void exitHeader(MusicParser.HeaderContext ctx) {
		  if(!setMeter)
			  song.setMeter(4, 4);
		  
		  if(!setLength) {
			  if(song.getMeterAsValue() < .75)
				  song.setLength(1.0 / 16);
			  
			  song.setLength(1.0 / 8);
		  }
		  
		  if(!setTempo) {
			  song.setTempo(song.getLength(), 100);
		  }
		  
		  if(currentVoice == "") {
			  currentVoice = "default";
			  song.addVoice(currentVoice);
		  }
	  }
	  
	  /**
	   * Set index number of Song
	   */
	  @Override public void exitField_number(MusicParser.Field_numberContext ctx) {
		  song.setIndex(Integer.valueOf(ctx.DIGIT().toString()));
	  }
	  
	  /**
	   * Set Song title
	   */
	  @Override public void exitField_title(MusicParser.Field_titleContext ctx) {
		  song.setTitle(ctx.TITLE().toString().substring(2).strip());
	  }
	  
	  /**
	   * Set composer of Song
	   */
	  @Override public void exitField_composer(MusicParser.Field_composerContext ctx) {
		  song.setComposer(ctx.COMPOSER().toString().substring(2).strip());
	  }
	  
	  /**
	   * Set default note length of Song
	   */
	  @Override public void exitField_default_length(MusicParser.Field_default_lengthContext ctx) {
		  double numerator = Double.valueOf(ctx.note_length_strict().DIGIT().get(0).toString());
		  int denominator = Integer.valueOf(ctx.note_length_strict().DIGIT().get(1).toString());
		  
		  song.setLength(numerator / denominator);
		  song.setTicksPerBeat(denominator);
		  
		  this.setLength = true;
	  }
	  
	  /**
	   * Add new voice, if not present, and add subsequent Music elements to the
	   * Music object associated with this voice
	   */
	  @Override public void exitField_voice(MusicParser.Field_voiceContext ctx) {
		  String voice = ctx.getText().substring(2).strip();
		  
		  currentVoice = voice;
		  
		  if(!voices.containsKey(voice)) {
			  song.addVoice(voice);
		  }
		  
		  this.barlineOffset.clear();
	  }

	  /**
	   * Add meter information to Song
	   */
	  @Override public void exitMeter(MusicParser.MeterContext ctx) {
		  song.setMeter(Integer.valueOf(ctx.meter_fraction().DIGIT().get(0).toString()),
				  Integer.valueOf(ctx.meter_fraction().DIGIT().get(1).toString()));
		  
		  this.setMeter = true;
	  }

	  /**
	   * Add tempo information to Song
	   */
	  @Override public void exitTempo(MusicParser.TempoContext ctx) {
		  song.setTempo(Double.valueOf(ctx.meter_fraction().DIGIT().get(0).toString()) /
				  Double.valueOf(ctx.meter_fraction().DIGIT().get(1).toString()), 
				  Integer.valueOf(ctx.DIGIT().toString()));
		  
		  this.setTempo = true;
	  }

	  /**
	   * Add key information to Song
	   */
	  @Override public void exitKey(MusicParser.KeyContext ctx) {
		  song.setKey(ctx.getText().substring(2));
		  String key = ctx.getText().substring(2);
		  
		  if(key.endsWith("m")) {
			  this.keyOffset = MakeSong.PITCH_OFFSETS.get(MinorKeys.valueOf(
					  keyMap.get(key.substring(0, key.length() - 1)))
					  .ordinal());
		  }
		  else {
			  this.keyOffset = MakeSong.PITCH_OFFSETS.get(MajorKeys.valueOf(
					  keyMap.get(key))
					  .ordinal());
		  }
	  }
	  
	  /**
	   * Add Music element with type and properties determined by descendant nodes, or parse repeat token, and
	   * remove all temporary overrides of the key signature upon entering a new bar
	   */
	  @Override public void exitElement(MusicParser.ElementContext ctx) {
		  if(!stack.empty()) {			  
			  song.addElement(currentVoice, stack.pop());
			  
			  return;
		  }
		  
		  if(ctx.BARLINE() != null) {
			  this.barlineOffset.clear();
		  }
		  
		  else if(ctx.REPEAT() != null) {
			  switch(ctx.REPEAT().getText()) {
			  	case "|:":
			  		song.resetRepeat(this.currentVoice);
			  		song.setPauseRepeat(false);
			  		break;
			  	case ":|":
			  		song.applyRepeat(this.currentVoice);
			  	case "|]":
			  		song.setPauseRepeat(false);
			  }
		  }
		  
		  else if(ctx.NTH_REPEAT() != null) {
			  if(ctx.NTH_REPEAT().getText().equals("[1"))
				  song.setPauseRepeat(true);
		  }
	  }
	  
	// calculate the required multiple to obtain an integer duration of ticks in a Note after accounting
		  // for the Song's tempo
		  private int calculateMultiple(int duration, int ticksPerBeat) {
			  int multiply = 1;
			  while(duration * song.noteLengthToTempoRatio() - 
					  Math.round(duration * song.noteLengthToTempoRatio()) > 0.00001 || 
					  multiply > 100) {
				  
				  multiply++;
				  
				  duration = duration * multiply;
				  ticksPerBeat = ticksPerBeat * multiply;
				  
			  }
			  
			  return multiply;
		  }
	  
	  @Override public void exitNote(MusicParser.NoteContext ctx) {
		  int duration = this.lengthToTicks(ctx.note_length());
		  int ticksPerBeat = song.getTicksPerBeat();
		  
		  int multiple = calculateMultiple(duration, ticksPerBeat);
		  duration = duration * multiple;
		  ticksPerBeat = ticksPerBeat * multiple;
		  
		  duration = (int)Math.round(duration * song.noteLengthToTempoRatio());
		  
		  if(ctx.note_or_rest().REST() != null)
			  stack.push(new Rest(duration, ticksPerBeat));
		  
		  else		  
			  stack.push(new Note(duration, ticksPerBeat, pitchStack.pop()));
		  
		  if(multiple > 1)
			  song.setTicksPerBeat(Music.leastCommonTicksPerBeat(Arrays.asList(song.getTicksPerBeat(), ticksPerBeat)));
	  }
	  
	  /**
	   * Push new Pitch object onto the stack with properties determined by pitch token
	   */
	  @Override public void exitPitch(MusicParser.PitchContext ctx) {
		  String note = ctx.BASENOTE().getText();
		  String octave = (ctx.OCTAVE() == null ? "" : ctx.OCTAVE().getText());
		  int offset = 0;
		  
		  if(ctx.accidental() != null) {
			  
			  switch(ctx.accidental().getText()) {
			  	case "=":
			  		offset += 0;
			  		break;
			  	case "^":
			  		offset += 1;
			  		break;
			  	case "^^":
			  		offset += 2;
			  		break;
			  	case "_":
			  		offset += -1;
			  		break;
			  	case "__":
			  		offset += -2;
			  		break;
			  }
			  
			  this.barlineOffset.put(note + octave, offset);
		  }
		  else {
			  if(barlineOffset.containsKey(note))
				  offset += barlineOffset.get(note);
			  else
				  offset += this.keyOffset.get(note.toUpperCase().charAt(0) - 'A');
		  }
		  
		// lowercase letters are one octave up
		  if(note != note.toUpperCase())
			  offset += Pitch.OCTAVE;
		  
		  // change amount to transpose pitch based on octave symbols
		  if(octave != "") {
			  if(octave.charAt(0) == '\'')
				  offset += Pitch.OCTAVE * octave.length();
			  else // ',' symbol
				  offset -= Pitch.OCTAVE * octave.length();
		  }
		  
		  pitchStack.push(new Pitch(note.toUpperCase().charAt(0)).transpose(offset));
	  }

	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitTuplet_element(MusicParser.Tuplet_elementContext ctx) {
		  Stack<Note> tupletNotes = new Stack<Note>();
		  
		  Note note = (Note)stack.peek(); // don't want to remove from stack until while loop below
		  int duration = note.getDuration();
		  int ticksPerBeat = note.getTicksPerBeat();
		  
		  switch(ctx.getText().substring(0, 2)) {
		  	case "(2":
		  		
		  		if(duration % 2 != 0) {
		  			duration *= 3;
		  			ticksPerBeat *= 2;
		  		}
		  		else
		  			duration = duration * 3 / 2;
		  		break;
		  	case "(3":
		  		if(duration % 3 != 0) {
		  			duration *= 2;
		  			ticksPerBeat *= 3;
		  		}
		  		else
		  			duration = duration * 2 / 3;
		  		break;
		  	case "(4":
		  		if(duration % 4 != 0) {
		  			duration *= 3;
		  			ticksPerBeat *= 4;
		  		}
		  		else
		  			duration = duration * 3 / 4;
		  		break;
		  }
		  
		  while(!stack.empty()) {
			  note = (Note)stack.pop();	  
			  tupletNotes.push(new Note(duration, ticksPerBeat, note.getPitch()));
		  }
		  
		  while(!tupletNotes.empty()) {
			  song.addElement(currentVoice, tupletNotes.pop());
			  song.setTicksPerBeat(Music.leastCommonTicksPerBeat(Arrays.asList(
					  song.getTicksPerBeat(), ticksPerBeat)));
		  }
	  }

	  /**
	   * Add Chord to stack with notes based on multi_note non-terminal
	   */
	  @Override public void exitMulti_note(MusicParser.Multi_noteContext ctx) {
		  List<Note> notes = new ArrayList<Note>();
		  
		  while(!stack.empty()) {
			  notes.add((Note)stack.pop()); // stack should only contain Note objects at this point
		  }
		  
		  stack.push(new Chord(notes));
	  }
	  
	  /** Set up mappings of key signature strings to MajorKeys enum which corresponds to indices in list of
		 * 	pitch offsets (PITCH_OFFSETS field)
		 */
		private Map<String, String> generateKeyMap() {
			Map<String, String> keyDict = new HashMap<String, String>();
			
			keyDict.put("A", "A");
			keyDict.put("Ab", "Ab");
			keyDict.put("A#", "Bb");
			
			keyDict.put("B", "B");
			keyDict.put("B#", "C");
			keyDict.put("Bb", "Bb");
			
			keyDict.put("C", "C");
			keyDict.put("C#", "Db");
			keyDict.put("Cb", "B");
			
			keyDict.put("D", "D");
			keyDict.put("D#", "Eb");
			keyDict.put("Db", "Db");
			
			keyDict.put("E", "E");
			keyDict.put("E#", "F");
			keyDict.put("Eb", "Eb");
			
			keyDict.put("F", "F");
			keyDict.put("F#", "Fs");
			keyDict.put("Fb", "E");
			
			keyDict.put("G", "G");
			keyDict.put("G#", "Ab");
			keyDict.put("Gb", "Fs");
			
			return new HashMap<String, String>(keyDict);
		}
	  
	  // Convert note_length non-terminal into a value which can be fed into a Music object
	  private int lengthToTicks(MusicParser.Note_lengthContext lengthContext) {
		  if(lengthContext == null)
			  return song.getTicksPerBeat();
		  
		  String noteLength = lengthContext.getText();
		  
		  // default values correspond to noteLength == '/'
		  int numerator = 1;
		  int denominator = 2;
		  
		  // x
		  if(noteLength.matches("\\d+")) {
			  denominator = 1;
			  numerator = Integer.valueOf(noteLength);
		  }
		  
		  // x/y
		  else if(noteLength.matches("\\d+/\\d+")) {
			  numerator = Integer.valueOf(noteLength.split("/")[0]);
			  denominator = Integer.valueOf(noteLength.split("/")[1]);
		  }
		  
		  // /y
		  else if(noteLength.matches("/\\d+")) {
			  denominator = Integer.valueOf(noteLength.substring(1));
		  }
		  
		  // x/
		  else if(noteLength.matches("\\d+/")) 
			  numerator = Integer.valueOf(noteLength.split("/")[0]);
		  
		  // if denominator is higher than current number of ticks per beat, update ticksPerBeat variable
		  // to denominator
		  if(song.getTicksPerBeat() < denominator)
			  song.setTicksPerBeat(Music.leastCommonTicksPerBeat(Arrays.asList(song.getTicksPerBeat(), denominator)));
		  
		  return numerator * song.getTicksPerBeat() / denominator;
	  }
}
