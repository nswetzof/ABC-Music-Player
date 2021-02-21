package music;

import abc.sound.SequencePlayer;
import abc.sound.Pitch;

public class Note implements Music {
	private final int duration;
	private final Pitch pitch;
	
	/*
	 * Rep invariant:
	 * 	duration >= 0;
	 * Abstraction function:
	 * 	Represents a note with pitch given by the 'pitch' field of 'duration' number of beats
	 */
	
	/**
	 * Make a musical note
	 * @param dur duration of the note in beats
	 * @param p pitch of the note
	 */
	public Note(int dur, Pitch p) {
		this.duration = dur;
		this.pitch = p;
		
		checkRep();
	}
	
	/**
	 * @return duration of note
	 */
	@Override
	public int getDuration() {
		return duration;
	}
	
	/**
	 * Play the note for its duration.
	 * @param player object which stores data for the musical composition
	 * @param atTick When to play measured in the number of ticks from the beginning of the song. The time per tick
	 * is defined in the player parameter's fields. 
	 */
	@Override
	public void play(SequencePlayer player, int atTick) {
//		player.addNote(pitch.toMidiNote(), atTick * player., numTicks); // TODO: Fix. Want to change to tick-based instead of beat-based classes
																		// TODO: Add ticksPerBeat to classes and fix tests
		throw new RuntimeException("Not implemented");
	}
	
	/**
	 * @return if 0 duration, then empty string;
	 * 	otherwise string representation of the note in abc format followed by duration. If duration is fractional, the
	 * 	numerator will always be displayed, even if equal to 1
	 */
	@Override
	public String toString() {
		throw new RuntimeException("Not implemented");
	}
	
	private void checkRep() {
		assert(duration >= 0);
	}
}
