package music;

import abc.sound.SequencePlayer;
import abc.sound.Pitch;

public class Note implements Music {
	private final double duration;
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
	public Note(double dur, Pitch p) {
		this.duration = dur;
		this.pitch = p;
		
		checkRep();
	}
	
	/**
	 * @return duration of note
	 */
	@Override
	public double getDuration() {
		return duration;
	}
	
	/**
	 * Play the note for its duration.
	 * @param player object which stores data for the musical composition
	 * @param atBeat When to play measured in the number of beats from the beginning of the song. The time per beat
	 * is defined in the player parameter's fields. 
	 */
	@Override
	public void play(SequencePlayer player, double atBeat) {
		throw new RuntimeException("Not implemented");
	}
	
	//TODO: might get rid of for all Music classes
	/**
	 * @return if 0 duration, then empty string;
	 * 	otherwise string representation of the note in abc format followed by duration
	 */
	@Override
	public String toString() {
		throw new RuntimeException("Not implemented");
	}
	
	private void checkRep() {
		assert(duration >= 0);
	}
}
