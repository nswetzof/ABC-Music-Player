package music;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

import abc.sound.SequencePlayer;

/** Represents multiple notes playing at the same time **/
public class Chord implements Music {
	private final List<Note> notes;
	private final double duration;
	
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
		
		// find minimum duration
		Stream<Note> noteStream = this.notes.stream();
		this.duration = noteStream.map(x -> x.getDuration())
				.reduce((x, y) -> {if(x < y) return x; return y;})
				.get();
		
		checkRep();
	}
	
	/**
	 * @return duration of the shortest note
	 */
	@Override
	public double getDuration() {
		return duration;
	}
	
	/**
	 * Play the chord.
	 * @param player object which stores data for the musical composition
	 * @param atBeat When to play measured in the number of beats from the beginning of the song. The time per beat
	 * is defined in the player parameter's fields. 
	 */
	@Override
	public void play(SequencePlayer player, double atBeat) {
		throw new RuntimeException("Not implemented");
	}
	
	/**
	 * @return string representation of the chord in abc format.
	 */
	@Override
	public String toString() {
		throw new RuntimeException("Not implemented");
	}
	
	private void checkRep() {
		assert(this.notes.size() > 0);
		assert(this.duration >= 0);
	}
}
