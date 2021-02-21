package music;

import static org.junit.Assert.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import music.Rest;
import abc.sound.SequencePlayer;

public class RestTest {
	/*
	 * Testing strategy for Rest:
	 * 	Partition the inputs as follows:
	 * 		durations: 0, integer number of beats, fractional number of beats
	 * 		starting tick: 0, n
	 * 	
	 * 	Verify Rest object matches expected after being fed into a SequencePlayer using play method
	 */

	@Test
	public void testRest() {
		try {
			final int ticksPerBeat = 4;
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			Rest r1 = new Rest(0, ticksPerBeat);
			Rest r2 = new Rest(4, ticksPerBeat);
			Rest r3 = new Rest(1, ticksPerBeat);
			
			assertEquals(r1.toString(), "");
			assertEquals(r2.toString(), ".");
			assertEquals(r3.toString(), ".1/4");
			
			// test duration = 0, start at tick 0
			r1.play(player1, 0);
			assertEquals(player2.toString(), player1.toString());
			
			// test duration = 0, start at tick n
			r1.play(player1, 3);
			assertEquals(player2.toString(), player1.toString());
			
			// test duration is integer number of beats, start at tick 0
			r2.play(player1, 0);
			assertEquals(player2.toString(), player1.toString());
			
			// test duration is integer number of beats, start at tick n
			r2.play(player1, 3);
			assertEquals(player2.toString(), player1.toString());
			
			// test duration is fractional number of beats, start at beat 0
			r3.play(player1, 0);
			assertEquals(player2.toString(), player1.toString());
			
			// test duration is fractional number of beats, start at tick n
			r3.play(player1, 3);
			assertEquals(player2.toString(), player1.toString());
			
		} catch (MidiUnavailableException mue) {
	        mue.printStackTrace();
	    } catch (InvalidMidiDataException imde) {
	        imde.printStackTrace();
	    }
	}

}
