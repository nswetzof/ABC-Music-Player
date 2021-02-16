package music;

import abc.sound.SequencePlayer;

/** Represents a musical composition. **/
public interface Music {
	/**
	 * @return duration of the musical composition
	 */
	public double getDuration();
	
	/**
	 * Play this piece.
	 * @param player object which stores data for the musical composition
	 * @param atBeat When to play measured in the number of beats from the beginning of the song. The time per beat
	 * is defined in the player parameter's fields. 
	 */
	public void play(SequencePlayer player, double atBeat);
}
