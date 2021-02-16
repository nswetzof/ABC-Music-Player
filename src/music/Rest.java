package music;

import abc.sound.SequencePlayer;

/** Represents a rest between notes in a musical composition **/
public class Rest implements Music {
	private final double duration;
	
	/*
	 * Rep invariant:
	 * 	duration >= 0
	 * Abstraction function:
	 * 	Represents a rest in the music of 'duration' number of beats
	 * Safety from rep exposure:
	 * 	duration is private and final
	 */
	
	/**
	 * Make a musical rest
	 * @param dur duration of the rest
	 */
	public Rest(double dur) {
		this.duration = dur;
		
		checkRep();
	}
	
	/**
	 * @return duration of rest
	 */
	@Override
	public double getDuration() {
		return duration;
	}
	
	/**
	 * Play no sound for the duration defined within this object.
	 * @param player object which stores data for the musical composition
	 * @param atBeat When to rest measured in the number of beats from the beginning of the song. The time per beat
	 * is defined in the player parameter's fields. 
	 */
	@Override
	public void play(SequencePlayer player, double atBeat) {
		throw new RuntimeException("Not implemented");
	}
	
	/**
	 * String representation of rest object
	 * @return 'z' symbol
	 */
	@Override
	public String toString() {
		throw new RuntimeException("Not implemented");
	}
	
	private void checkRep() {
		assert(duration >= 0);
	}
}
