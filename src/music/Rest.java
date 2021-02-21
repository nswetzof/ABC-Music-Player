package music;

import abc.sound.SequencePlayer;

/** Represents a rest between notes in a musical composition **/
public class Rest implements Music {
	private final int duration;
	private final int ticksPerBeat;
	
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
	 * @param perBeat number of ticks per beat
	 */
	public Rest(int dur, int perBeat) {
		this.duration = dur;
		this.ticksPerBeat = perBeat;
		
		checkRep();
	}
	
	/**
	 * @return duration of rest
	 */
	@Override
	public int getDuration() {
		return duration;
	}
	
	/**
	 * Play no sound for the duration defined within this object.
	 * @param player object which stores data for the musical composition
	 * @param atTick When to rest measured in the number of ticks from the beginning of the song. The time per tick
	 * is defined in the player parameter's fields. 
	 */
	@Override
	public void play(SequencePlayer player, int atTick) {
		return;
	}
	
	/**
	 * String representation of rest object
	 * @return '.' symbol followed by duration.  If duration is fractional, the
	 * 	numerator will always be displayed, even if equal to 1
	 */
	@Override
	public String toString() {
		if(this.getDuration() == (int)(this.getDuration()))
			return "." + (int)(this.getDuration());
		
		return "." + Music.fractionToString(this.getDuration());
	}
	
	private void checkRep() {
		assert(duration >= 0);
	}
}
