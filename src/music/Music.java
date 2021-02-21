package music;

import abc.sound.SequencePlayer;

/** Represents a musical composition. **/
public interface Music {
	/**
	 * @return duration of the musical composition in ticks
	 */
	public int getDuration();
	
	/**
	 * Play this piece.
	 * @param player object which stores data for the musical composition
	 * @param atTick When to play measured in the number of ticks from the beginning of the song. The time per tick
	 * is defined in the player parameter's fields. 
	 */
	public void play(SequencePlayer player, int atTick);
	
	/**
	 * Return the greatest common divisor of a fraction
	 * @param numerator the numerator
	 * @param denominator the denominator
	 * @return greatest common divisor of the fraction represented by numerator / denominator
	 **/
	public static int greatestCommonDivisor(int numerator, int denominator) {
		int gcd = 1;
		for(int i = 2; i < Math.max(numerator, denominator); i++) {
			if(numerator % i == 0 && denominator % i == 0) {
				gcd = i;
			}
		}
		
		return gcd;
	}
}
