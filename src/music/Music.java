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
	 * @param fractionalValue double to be represented
	 * @return string representation of double value converted to a fraction
	 * @throws ArithmeticException if fractionalValue cannot be represented by a fraction with denominator <= 32
	 */
	public static String fractionToString(double fractionalValue) throws ArithmeticException {
		final double epsilon = 0.000001;
		
		if((int)fractionalValue == fractionalValue)
			return String.valueOf((int)fractionalValue);
		
		int denom = 2;
		double newValue = fractionalValue;
		
		// find an integer denominator
		while(denom <= 32) {
			newValue = fractionalValue * denom;
			if(newValue % 1 < epsilon)
				break;
			
			denom++;
		}
		
		if(denom > 32)
			throw new ArithmeticException(fractionalValue + " cannot be represented by a fraction with denominator" +
				" <= 32.");
		
		int num = (int)Math.round(newValue);
		int gcd = Music.greatestCommonDivisor(num, denom);
		num /= gcd;
		denom /= gcd;
		
		return String.valueOf(num) + "/" + String.valueOf(denom);
	}
	
	/*
	 * Return the greatest common divisor of a fraction
	 * @param numerator the numerator
	 * @param denominator the denominator
	 * @return greatest common divisor of the fraction represented by numerator / denominator
	 */
	private static int greatestCommonDivisor(int numerator, int denominator) {
		int gcd = 1;
		for(int i = 2; i < Math.max(numerator, denominator); i++) {
			if(numerator % i == 0 && denominator % i == 0) {
				gcd = i;
			}
		}
		
		return gcd;
	}
}
