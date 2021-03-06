package music;

import java.io.IOException;

import abc.sound.SequencePlayer;

/** Represents a musical composition. **/
public interface Music {
	/**
	 * @return duration of the musical composition in ticks
	 */
	public int getDuration();
	
	/**
	 * @return number of ticks that make up a beat.  This value will be such that the duration of any note in the piece 
	 * 			can be represented by a whole number of ticks
	 */
	public int getTicksPerBeat(); // TODO: There will be some rep invariants with this field for Chord and Concat at least
	
	/**
	 * Set the number of ticks per beat.  Walk through all member Music objects and set ticks per beat to 'ticks'
	 * 	for each of them recursively.
	 */
	public void setTicksPerBeat(int ticks);
	
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
	
	/**
	 * Parses an abc file and converts it to a Music object
	 * @param file name and path of abc file for the parser to interpret
	 * @return Music object with properties and musical components defined in file
	 * @throws IOException if error opening file
	 */
	public static Music parse(String file) throws IOException {
		
		throw new RuntimeException("not implemented");
	}
	
	/**
	 * Parses a String and converts it to a Music object.
	 * @param abcString String representing the contents of an abc file
	 * @return Music object with properties and musical components defined in abcString
	 */
	public static Music parseString(String abcString) {
		throw new RuntimeException("not implemented");
	}
}
