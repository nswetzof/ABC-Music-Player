package abc.parser;

import java.util.*;
import java.util.stream.Stream;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import abc.sound.SequencePlayer;
import music.*;


public class MakeSong extends MusicBaseListener {
	private Map<String, Music> voices = new HashMap<String, Music>();
	private Stack<Integer> intStack = new Stack<Integer>();
	private Stack<Music> stack = new Stack<Music>();
	
	private boolean setLength = false;
	private boolean setMeter = false;
	private boolean setTempo = false;
	
//	private final Map<String, Map<String, String>> keySignatures;
//	private final Map<String, String> minorKeys;
	
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
		  double denominator = Double.valueOf(ctx.note_length_strict().DIGIT().get(1).toString());
		  
		  song.setLength(numerator / denominator);
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
		  
		// No listener for REST terminal so it will not push to the stack
		  if(stack.empty())
			  stack.push(new Rest(???)); // TODO: need function to parse note_length tokens
		  
		  else {
			  stack.push(new Note(???)) // TODO: need function to parse BASENOTE tokens into pitches based on key signature
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
	  @Override public void exitPitch(MusicParser.PitchContext ctx) { }
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
	  
	  // Convert note_length non-terminal into a value which can be fed into a Music object
	  private int lengthToTicks(MusicParser.Note_lengthContext lengthContext) {
		  String noteLength = lengthContext.getText();
		  
		  int numerator = 1;
		  int denominator = 2;
		  
		  if(noteLength.matches("\\d+")) {
			  denominator = 1;
			  numerator = Integer.valueOf(noteLength) * song.getTicksPerBeat();
		  }
		  
		  else if(noteLength.matches("\\d+/\\d+")) {
			  numerator = Integer.valueOf(noteLength.split("/")[0]);
			  denominator = Integer.valueOf(noteLength.split("/")[1]);
		  }
		  
		  else if(noteLength.matches("/\\d+")) {
			  denominator = Integer.valueOf(noteLength.substring(1));
		  }
		  
		  else
			  numerator = Integer.valueOf(noteLength.split("/")[0]);
		  
		  // if denominator is higher than current number of ticks per beat, update ticksPerBeat variable
		  // to denominator
		  if(song.getTicksPerBeat() < denominator)
			  song.setTicksPerBeat(denominator);
		  
		  return numerator * song.getTicksPerBeat() / denominator;
	  }
}
