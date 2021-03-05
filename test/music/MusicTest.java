package music;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import abc.sound.SequencePlayer;

public class MusicTest {
	
	private String abcString1 = "X: 1\nT: Song1\nK: C\n";

	/*
	 * Testing strategy for Music.parse(String file):
	 * 	Partition the inputs as follows:
	 * 		Music:
	 * 		lines in file: 0, 1, n
	 * 		voices: 1, n
	 * 		file contains: rest, note, chord, repeat, duplet, triplet, quadruplet
	 * 		note in piece with length: 1, n, m/n where m%n != 0
	 * 		relative note lengths: same lengths, different lengths
	 * 		note in file has accidental: natural, sharp, double sharp, flat, double flat
	 * 		chord in file: all notes same length, some notes different lengths
	 * 		octave symbols present: none, 1 apostrophe, n apostrophes, 1 comma, n commas
	 * 		repeat: begin repeat bar present, begin repeat bar absent, same endings, different endings
	 * 		
	 * 	Verify generated music matches expected for significant sample of partitions using sample music pieces
	 * 
	 *	Test with multiple default note lengths, meters, tempos, and key signatures to ensure Music objects 
	 *		generated will change properties accordingly.
	 */
	
	@Test
	public void testParseNoNotes() {
		try {
			Music m = Music.parse(abcString1);
			
			final int ticksPerBeat = m.getTicksPerBeat(); 
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			
			
		} catch(IOException ioe) {
			fail(ioe.getStackTrace().toString());
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}

}
