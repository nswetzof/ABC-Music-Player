package music;

import abc.sound.SequencePlayer;
import abc.sound.Pitch;

public class Note implements Music {
	private int duration;
	private int ticksPerBeat;
	private final Pitch pitch;
	
	/*
	 * Rep invariant:
	 * 	duration >= 0;
	 * Abstraction function:
	 * 	Represents a note with pitch given by the 'pitch' field of 'duration' number of beats
	 * Safety from rep exposure:
	 * 	TODO: complete
	 */
	
	/**
	 * Make a musical note
	 * @param dur duration of the note in ticks
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
	 * @return pitch of the note
	 */
	public Pitch getPitch() {
		Pitch p = new Pitch('C');
		p = this.pitch;
		
		return p;
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
	
	@Override
	public int getTicksPerBeat() {
		int oldTicksPerBeat = this.ticksPerBeat;
		return this.ticksPerBeat;
		
	}
	
	@Override
	public void setTicksPerBeat(int ticks) {
		int oldTicksPerBeat = this.ticksPerBeat;
		
		this.ticksPerBeat = ticks;
		
		this.duration *= this.ticksPerBeat / oldTicksPerBeat;
	}
	
	// TODO: if numerator in a fraction is equal to 1, don't want to show (same for rests)
	/**
	 * @return if 0 duration, then empty string;
	 * 	otherwise string representation of the note in abc format followed by duration. If duration is fractional, the
	 * 	numerator will always be displayed, even if equal to 1, and the fraction will be reduced to its lowest terms.
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
	
	/**
	 * Compare pitch of this object with pitch of another Note object
	 * @param that note to be compared
	 * @return 0 if notes are equal in pitch, -1 if this note's pitch is less than that's, 1 otherwise
	 */
	public int compare(Note that) {
		if(this.pitch == that.pitch)
			return 0;
		if(this.pitch.lessThan(that.pitch))
			return -1;
		return 1;
	}
	
	private void checkRep() {
		assert(duration >= 0);
	}
}
