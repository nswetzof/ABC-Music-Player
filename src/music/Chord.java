package music;

import java.util.List;
import java.util.ArrayList;

import abc.sound.SequencePlayer;

/** Represents multiple notes playing at the same time **/
public class Chord implements Music {
	private final List<Note> notes;
	private int duration;
	private int ticksPerBeat;
	
	/*
	 * Rep invariant:
	 * 	duration >= 0
	 * 	notes.size > 0
	 * Abstraction function:
	 * 	Each note in notes represents a note playing in the song.  All notes have the same start time.
	 * 	duration represents the shortest duration note in the chord
	 * Safety from rep exposure:
	 * 	All fields are private and final;
	 * 	duration is a double, so is guaranteed immutable
	 * 	notes is mutable, so defensive copying is used in the constructor and observer methods
	 */
	
	/**
	 * Make a chord
	 * @param n notes in the chord
	 * @requires n has at least one note
	 */
	public Chord(List<Note> n) {
		this.notes = new ArrayList<Note>(n);
		this.notes.sort((x, y) -> x.compare(y));
		
		List<Integer> tickValues = new ArrayList<Integer>();
		
		for(Note note : this.notes) {
			tickValues.add(note.getTicksPerBeat());
		}
		
		this.ticksPerBeat = Music.leastCommonTicksPerBeat(tickValues);
		
		duration = this.notes.get(0).getDuration();
		
		checkRep();
	}
	
	/**
	 * @return duration of the first note
	 */
	@Override
	public int getDuration() {
		return duration;
	}
	
	@Override
	public int getTicksPerBeat() {
		return this.ticksPerBeat;
	}
	
	@Override
	public void setTicksPerBeat(int ticks) {
		int oldTicksPerBeat = this.getTicksPerBeat();
		
		this.ticksPerBeat = ticks;
		this.duration = ticks / oldTicksPerBeat * this.duration;
		
		for(Note note : this.notes) {
			note.setTicksPerBeat(ticks);
		}
	}
	
	/**
	 * Play the chord.
	 * @param player object which stores data for the musical composition
	 * @param atTick When to play measured in the number of ticks from the beginning of the song. The time per tick
	 * is defined in the player parameter's fields. 
	 */
	@Override
	public void play(SequencePlayer player, int atTick) {
		for(Note note : this.notes) {
			player.addNote(note.getPitch().toMidiNote(), atTick, note.getDuration());
		}
	}
	
	/**
	 * @return string representation of the chord in abc format.  Notes in the chord are displayed in ascending order.
	 */
	@Override
	public String toString() {		
		StringBuilder sb = new StringBuilder("");
		sb.append('[');
		
		for(Note note : this.notes)
			sb.append(note.toString());
		
		sb.append(']');
		
		return sb.toString();
	}
	
	private void checkRep() {
		assert(this.notes.size() > 0);
		assert(this.duration >= 0);
	}
}
