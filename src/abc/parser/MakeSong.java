package abc.parser;

import java.util.*;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import abc.sound.SequencePlayer;
import music.Music;

/** Stores information found in the header of an abc file **/
class Header {
	private int index;
	private String title;
	private String composer;
	private List<Integer> meter;
	private int tempo;
	private String key;
	
	public Header() {
		this.index = 0;
		this.title = "";
		this.composer = "Unknown";
		this.meter.add(4);
		this.meter.add(4);
		this.tempo = 100;
		this.key = "";
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getComposer() {
		return composer;
	}
	
	public List<Integer> getMeter() {
		return meter;
	}
	
	public int getTempo() {
		return tempo;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setIndex(int i) {
		this.index = i;
	}
	
	public void setTitle(String t) {
		this.title = t;
	}
	
	public void setComposer(String c) {
		this.composer = c;
	}
	
	public void setMeter(int num, int denom) {
		assert(meter.size() == 2);
		
		this.meter.set(0, num);
		this.meter.set(1, denom);
	}
	
	public void setTempo(int t) {
		this.tempo = t;
	}
	
	public void setKey(String k) {
		this.key = k;
	}
}

public class MakeSong extends MusicBaseListener {
	private Stack<Music> stack = new Stack<Music>();
	Header header = new Header();
	
	/**
	 * @return SequencePlayer object initialized using information contained in the abc file
	 */
	public SequencePlayer getPlayer() {
		throw new RuntimeException("not implemented");
	}
	
	/**
	 * @return a Music object and header information that can be fed to a SequencePlayer object.
	 */
	public Music getMusic() {
		return stack.get(0);
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
	  @Override public void exitHeader(MusicParser.HeaderContext ctx) { }
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
	  @Override public void exitField_number(MusicParser.Field_numberContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterComment(MusicParser.CommentContext ctx) { }
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
	  @Override public void enterField_default_length(MusicParser.Field_default_lengthContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitField_default_length(MusicParser.Field_default_lengthContext ctx) { }
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
	  @Override public void exitField_meter(MusicParser.Field_meterContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterField_tempo(MusicParser.Field_tempoContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitField_tempo(MusicParser.Field_tempoContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterField_voice(MusicParser.Field_voiceContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitField_voice(MusicParser.Field_voiceContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterField_key(MusicParser.Field_keyContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitField_key(MusicParser.Field_keyContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterNote_length_strict(MusicParser.Note_length_strictContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitNote_length_strict(MusicParser.Note_length_strictContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterMeter(MusicParser.MeterContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitMeter(MusicParser.MeterContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterMeter_fraction(MusicParser.Meter_fractionContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitMeter_fraction(MusicParser.Meter_fractionContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterTempo(MusicParser.TempoContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitTempo(MusicParser.TempoContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterKey(MusicParser.KeyContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitKey(MusicParser.KeyContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterKeynote(MusicParser.KeynoteContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitKeynote(MusicParser.KeynoteContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
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
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitNote_element(MusicParser.Note_elementContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterNote(MusicParser.NoteContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitNote(MusicParser.NoteContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void enterNote_or_rest(MusicParser.Note_or_restContext ctx) { }
	  /**
	   * {@inheritDoc}
	   *
	   * <p>The default implementation does nothing.</p>
	   */
	  @Override public void exitNote_or_rest(MusicParser.Note_or_restContext ctx) { }
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
}
