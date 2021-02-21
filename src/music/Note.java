package music;

import abc.sound.SequencePlayer;
import abc.sound.Pitch;

public class Note implements Music {
	private final int duration;
	private final int ticksPerBeat;
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
	 * @param perBeat number of ticks per beat
	 * @param p pitch of the note
	 */
	public Note(int dur, int perBeat, Pitch p) {
		this.duration = dur;
		this.ticksPerBeat = perBeat;
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
		player.addNote(pitch.toMidiNote(), atTick, this.getDuration());
	}
	
	/**
	 * @return if 0 duration, then empty string;
	 * 	otherwise string representation of the note in abc format followed by duration. If duration is fractional, the
	 * 	numerator will always be displayed, even if equal to 1
	 */
	@Override
	public String toString() {
		int dur = this.getDuration();
		
		if(dur == 0)
			return "";
		
		else if(dur == this.ticksPerBeat)
			return this.pitch.toString();
		
		else if(dur % this.ticksPerBeat == 0)
			return this.pitch.toString() + (dur / this.ticksPerBeat);
		
		else {
			int gcd = Music.greatestCommonDivisor(dur, this.ticksPerBeat);
			
			return this.pitch.toString() + (dur / gcd) + "/" + (this.ticksPerBeat / gcd);
		}
	}
	
	private void checkRep() {
		assert(duration >= 0);
	}
}
