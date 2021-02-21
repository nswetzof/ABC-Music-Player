package music;

import abc.sound.SequencePlayer;

/** Represents two pieces of music played one after the other **/
public class Concat implements Music {
	private final Music first, second;
	
	/*
	 * Rep invariant:
	 *  None
	 * Abstraction function:
	 * 	Represents two subsets of a musical composition given by 'first' and 'second'.
	 * 	'second plays immediately following 'first'
	 * Safety from rep exposure:
	 * 	All fields are private and final;
	 * 	'first' and 'second' are immutable so are safe from changes
	 */
	
	/**
	 * Make a combination of musical pieces played one after the other
	 * @param m1 first musical piece
	 * @param m2 second musical piece
	 */
	public Concat(Music m1, Music m2) {
		this.first = m1;
		this.second = m2;
		
		checkRep();
	}
	
	/**
	 * @return sum of durations of each musical piece
	 */
	@Override
	public int getDuration() {
		return this.first.getDuration() + this.second.getDuration();
	}
	
	/**
	 * Play both pieces of music sequentially
	 * @param player object which stores data for the musical composition
	 * @param atTick When to play the first piece of music measured in the number of ticks from the beginning of the song.
	 * The time per tick is defined in the player parameter's fields. 
	 */
	public void play(SequencePlayer player, int atTick) {
		first.play(player, atTick);
		second.play(player, atTick + first.getDuration());
	}
	
	/**
	 * @return string representation of both pieces back to back according to their toString methods
	 */
	public String toString() {
		if(first.getDuration() == 0)
			return second.toString();
		
		if(second.getDuration() == 0)
			return first.toString();
		
		return first.toString() + " " + second.toString();
	}
	
	private void checkRep() {
		return;
	}
}
