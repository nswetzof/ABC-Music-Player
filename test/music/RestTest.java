package Music;

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
	 * 		durations: 0, integer value, fractional value
	 * 		starting beat: 0, integer value, fractional value
	 * 	
	 * 	Verify Rest object matches expected after being fed into a SequencePlayer using play method
	 */

	@Test
	public void testRest() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			Rest r1 = new Rest(0);
			Rest r2 = new Rest(1);
			Rest r3 = new Rest(.5);
			
			assertEquals(r1.toString(), ".0");
			assertEquals(r2.toString(), ".1");
			assertEquals(r3.toString(), ".0.5");
			
			// test duration = 0, start at beat 0
			r1.play(player1, 0);
			assertEquals(player1.toString(), player2.toString());
			
			// test duration = 0, start at integer value
			r1.play(player1, 3);
			assertEquals(player1.toString(), player2.toString());
			
			// test duration = 0, start at fractional value
			r1.play(player1, 2.25);
			assertEquals(player1.toString(), player2.toString());
			
			// test duration is integer, start at beat 0
			r2.play(player1, 0);
			assertEquals(player1.toString(), player2.toString());
			
			// test duration is integer, start at integer value
			r2.play(player1, 3);
			assertEquals(player1.toString(), player2.toString());
			
			// test duration is integer, start at fractional value
			r2.play(player1, 2.25);
			assertEquals(player1.toString(), player2.toString());
			
			// test duration is fractional value, start at beat 0
			r3.play(player1, 0);
			assertEquals(player1.toString(), player2.toString());
			
			// test duration is fractional value, start at integer value
			r3.play(player1, 3);
			assertEquals(player1.toString(), player2.toString());
			
			// test duration is fractional value, start at fractional value
			r3.play(player1, 2.25);
			assertEquals(player1.toString(), player2.toString());
			
		} catch (MidiUnavailableException mue) {
	        mue.printStackTrace();
	    } catch (InvalidMidiDataException imde) {
	        imde.printStackTrace();
	    }
	}

}
