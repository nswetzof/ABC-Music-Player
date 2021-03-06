package music;

import java.util.List;

/** Represents all the header and body information necessary to create a MIDI song. **/ 
public class Song {
	private Music music;
	private int index;
	private String title;
	private String composer;
	private List<Integer> meter;
	private int tempo;
	private String key;
	
	/*
	 * Rep invariant:
	 * 	all fields are non-null;
	 * 	key must be in the set of major and minor keys in western musical notation
	 * 	meter.size() == 2;
	 * Abstraction function:
	 * 	Represents a song with notes defined in 'music' and header information obtained from an abc file
	 * 		defined in the remaining fields.
	 * Safety from rep exposure:
	 * 	
	 */
	
	public Song() {
		Music music = new Rest(0, 1); // TODO: confirm
		
		this.index = 0;
		this.title = "";
		this.composer = "Unknown";
		this.meter.add(4);
		this.meter.add(4);
		this.tempo = 100;
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
	 * @return meter of Song
	 */
	public List<Integer> getMeter() {
		return meter;
	}
	
	/**
	 * @return tempo of Song
	 */
	public int getTempo() {
		return tempo;
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
	 * Set meter of Song
	 * @param num number of beats per measure
	 * @param denom inverse of number of notes per beat (eg. denom of 4 represents 1/4 note per beat)
	 */
	public void setMeter(int num, int denom) {
		assert(meter.size() == 2);
		
		this.meter.set(0, num);
		this.meter.set(1, denom);
	}
	
	/**
	 * Set tempo of Song
	 * @param c number representing desired tempo
	 */
	public void setTempo(int t) {
		this.tempo = t;
	}
	
	/**
	 * Set key of Song
	 * @param c String representing desired key
	 * @requires k is a member of the set of major and minor keys in western musical notation 
	 */
	public void setKey(String k) {
		this.key = k;
	}
	
	private void checkRep() {
		assert(music != null);
		assert(title != null);
		assert(composer != null);
		assert(meter.size() == 2);
		assert(key != null);
	}
} // end class Song
