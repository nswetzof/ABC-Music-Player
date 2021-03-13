package abc.parser;

import static org.junit.Assert.assertThrows;

import java.util.*;
import java.util.stream.Stream;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import abc.sound.Pitch;
import abc.sound.SequencePlayer;
import music.*;
//import music.Song.MajorKeys;


public class MakeSong extends MusicBaseListener {
	private Map<String, Music> voices = new HashMap<String, Music>();
	private Stack<Integer> intStack = new Stack<Integer>();
	private Stack<Music> stack = new Stack<Music>();
	
	private boolean setLength = false;
	private boolean setMeter = false;
	private boolean setTempo = false;
	
	private final Map<String, MajorKeys> keyMap = generateKeyMap(); // maps key String to MajorKeys value
	private List<Integer> keyOffset; // store the pitch offsets for each note based on accidentals 
									 //	based on the 'key' field
	private Map<String, Integer> barlineOffset; // store pitch offsets to be applied to just the barline with keys
												// represented by the pitch and values represented by the pitch offsets
	
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
	
	Song song = new Song();
	private String currentVoice = "";
	
//	/**
//	 * @return SequencePlayer object initialized using information contained in the abc file
//	 */
//	public SequencePlayer getPlayer() {
//		throw new RuntimeException("not implemented");
//	}
	
	/**
	 * @return a Song object with header and music information that can be fed to a SequencePlayer object.
	 */
	public Song getSong() {
		return song;
	}
	
	/**
	 * @return String representing header information from the abc file
	 */
	public String toString() {
		throw new RuntimeException("not implemented");
	}
	
	/**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterRoot(MusicParser.RootContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitRoot(MusicParser.RootContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterHeader(MusicParser.HeaderContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
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
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterField_number(MusicParser.Field_numberContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitField_number(MusicParser.Field_numberContext ctx) {
		  song.setIndex(Integer.valueOf(ctx.DIGIT().toString()));
	  }

	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitComment(MusicParser.CommentContext ctx) { }
	  
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitField_title(MusicParser.Field_titleContext ctx) {
		  song.setTitle(ctx.TITLE().toString().substring(2).strip());
	  }
	  
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterOther_fields(MusicParser.Other_fieldsContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitOther_fields(MusicParser.Other_fieldsContext ctx) { }
	  
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitField_composer(MusicParser.Field_composerContext ctx) {
		  song.setComposer(ctx.COMPOSER().toString().substring(2).strip());
	  }
	  
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitField_default_length(MusicParser.Field_default_lengthContext ctx) {
		  double numerator = Double.valueOf(ctx.note_length_strict().DIGIT().get(0).toString());
		  int denominator = Integer.valueOf(ctx.note_length_strict().DIGIT().get(1).toString());
		  
		  song.setLength(numerator / denominator);
		  song.setTicksPerBeat(denominator);
		  
		  this.setLength = true;
	  }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterField_meter(MusicParser.Field_meterContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitField_meter(MusicParser.Field_meterContext ctx) {
		  
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
			  this.keyOffset = this.PITCH_OFFSETS.get((MinorKeys)this.keyMap.get(key.substring(0, key.length() - 1)));
			  this.keyOffset = this.PITCH_OFFSETS.get(this.keyMap.get(key.substring(0, key.length() - 1)).ordinal());
			  System.err.println(keyOffset.toString()); // TODO: FIX keyOffset VALUE
		  }
		  else {
			  System.err.println("Key: " + key);
			  this.keyOffset = this.PITCH_OFFSETS.get(this.keyMap.get(key).ordinal()); // TODO: FIX
		  }
	  }

	  /**
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterBody(MusicParser.BodyContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitBody(MusicParser.BodyContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterAbc_line(MusicParser.Abc_lineContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitAbc_line(MusicParser.Abc_lineContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterElement(MusicParser.ElementContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitElement(MusicParser.ElementContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterNote_element(MusicParser.Note_elementContext ctx) { }
	  
	  /**
	   * Add Music element with type and properties determined by descendant nodes
	   */
	  @Override public void exitNote_element(MusicParser.Note_elementContext ctx) {
		  song.addElement(currentVoice, stack.pop());
	  }
	  
	  @Override public void exitNote(MusicParser.NoteContext ctx) {
		  
		  System.out.println("Note Terminal: " + ctx.getText());
//		  System.out.println(this.lengthToTicks(ctx.note_length()));
		  // No listener for REST terminal, so it will not push to the stack
		  if(stack.empty())
			  stack.push(new Rest(this.lengthToTicks(ctx.note_length()), song.getTicksPerBeat()));
		  
		  else {
			  String note = ctx.note_or_rest().getText();
			  System.out.println("Note: " + note);
			  //stack.push(new Note(this.lengthToTicks(ctx.note_length()), song.getTicksPerBeat(),
			  //???)) // TODO: need function to parse BASENOTE tokens into pitches based on key signature
		  }
//		  stack.pop(item)
	  }

	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitNote_or_rest(MusicParser.Note_or_restContext ctx) {
	  }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterPitch(MusicParser.PitchContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitPitch(MusicParser.PitchContext ctx) {
		  System.out.println("Pitch: " + ctx.getText());
		  String note = ctx.BASENOTE().getText();
		  String octave = (ctx.OCTAVE() == null ? "" : ctx.OCTAVE().getText());
		  int offset = 0;
		  
		  Pitch p = new Pitch(note.toUpperCase().charAt(0));
		  
		  // lowercase letters are one octave up
		  if(note != note.toUpperCase())
			  offset += Pitch.OCTAVE;
//			  p.transpose(Pitch.OCTAVE);
		  
		  // change amount to transpose pitch based on octave symbols
		  if(octave != "") {
			  if(octave.charAt(0) == '\'')
				  offset += octave.length();
			  else // ',' symbol
				  offset -= octave.length();
		  }
		  
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
			  
//			  p.transpose(offset);
			  barlineOffset.put(note + octave, -2);			  
		  }
		  else {
			  offset += this.keyOffset.get(this.keyMap.get(note.toUpperCase()).ordinal());
//			  p.transpose(this.keyOffset.get(this.keyMap.get(note.toUpperCase()).ordinal()));
		  }
		  
		  p.transpose(offset);
		  System.err.println("offset: " + offset);
	  }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterNote_length(MusicParser.Note_lengthContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitNote_length(MusicParser.Note_lengthContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterTuplet_element(MusicParser.Tuplet_elementContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitTuplet_element(MusicParser.Tuplet_elementContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterTuplet_spec(MusicParser.Tuplet_specContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitTuplet_spec(MusicParser.Tuplet_specContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterMulti_note(MusicParser.Multi_noteContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitMulti_note(MusicParser.Multi_noteContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterMid_tune_field(MusicParser.Mid_tune_fieldContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitMid_tune_field(MusicParser.Mid_tune_fieldContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterAccidental(MusicParser.AccidentalContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitAccidental(MusicParser.AccidentalContext ctx) { }

	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterEveryRule(ParserRuleContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitEveryRule(ParserRuleContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void visitTerminal(TerminalNode node) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void visitErrorNode(ErrorNode node) { }
	  
	   // convert a list of String literals in a DIGIT terminal into a string representing the number
//	  private String digitToString(List<TerminalNode> l) {
//		  String number = l.stream()
//				  .map((x) -> x.toString())
//				  .reduce("", (x, y) -> x + y);
//		  
//		  return number;
//	  }
	  
	  /** Set up mappings of key signature strings to MajorKeys enum which corresponds to indices in list of
		 * 	pitch offsets (PITCH_OFFSETS field)
		 */
		private Map<String, MajorKeys> generateKeyMap() {
			Map<String, MajorKeys> keyDict = new HashMap<String, MajorKeys>();
			
			keyDict.put("A", MajorKeys.A);
			keyDict.put("Ab", MajorKeys.Ab);
			keyDict.put("A#", MajorKeys.Bb);
			
			keyDict.put("B", MajorKeys.B);
			keyDict.put("B#", MajorKeys.C);
			keyDict.put("Bb", MajorKeys.Bb);
			
			keyDict.put("C", MajorKeys.C);
			keyDict.put("C#", MajorKeys.Db);
			keyDict.put("Cb", MajorKeys.B);
			
			keyDict.put("D", MajorKeys.D);
			keyDict.put("D#", MajorKeys.Eb);
			keyDict.put("Db", MajorKeys.Db);
			
			keyDict.put("E", MajorKeys.E);
			keyDict.put("E#", MajorKeys.F);
			keyDict.put("Eb", MajorKeys.Eb);
			
			keyDict.put("F", MajorKeys.F);
			keyDict.put("F#", MajorKeys.Fs);
			keyDict.put("Fb", MajorKeys.E);
			
			keyDict.put("G", MajorKeys.G);
			keyDict.put("G#", MajorKeys.Ab);
			keyDict.put("Gb", MajorKeys.Fs);
			
			return new HashMap<String, MajorKeys>(keyDict);
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
			  numerator = Integer.valueOf(noteLength) * song.getTicksPerBeat();
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
			  song.setTicksPerBeat(denominator);
		  
		  return numerator * song.getTicksPerBeat() / denominator;
	  }
}
