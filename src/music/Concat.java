package music;

import java.util.Arrays;

import abc.sound.SequencePlayer;

/** Represents two pieces of music played one after the other **/
public class Concat implements Music {
	private final Music first, second;
	private int ticksPerBeat;
	
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
		
		if(m1.getTicksPerBeat() % m2.getTicksPerBeat() == 0 || m2.getTicksPerBeat() % m1.getTicksPerBeat() == 0)
			this.ticksPerBeat = Math.max(m1.getTicksPerBeat(), m2.getTicksPerBeat());
		else
			this.ticksPerBeat = Music.leastCommonTicksPerBeat(Arrays.asList(m1.getTicksPerBeat(), m2.getTicksPerBeat()));
		
		checkRep();
	}
	
	/**
	 * @return sum of durations of each musical piece
	 */
	@Override
	public int getDuration() {
		return this.first.getDuration() + this.second.getDuration();
	}
	
	@Override
	public int getTicksPerBeat() {
		return this.ticksPerBeat;
	}
	
	@Override
	public void setTicksPerBeat(int ticks) {
		this.ticksPerBeat = ticks;
		
		this.first.setTicksPerBeat(ticks);
		this.second.setTicksPerBeat(ticks);
	}
	
	/**
	 * Play both pieces of music sequentially
	 * @param player object which stores data for the musical composition
	 * @param atTick When to play the first piece of music measured in the number of ticks from the beginning of the song.
	 * The time per tick is defined in the player parameter's fields. 
	 */
	public void play(SequencePlayer player, int atTick) {
		first.setTicksPerBeat(this.ticksPerBeat);
		second.setTicksPerBeat(this.ticksPerBeat);
		
//		System.err.println("First duration: " + first.getDuration());
//		System.err.println("Second duration: " + second.getDuration());
		
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
