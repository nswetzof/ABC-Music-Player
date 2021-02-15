package abc.sound;

import static org.junit.Assert.*;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.InvalidMidiDataException;

import org.junit.Test;

public class SequencePlayerTest {

    // TODO: warmup #2
	
	private Pitch A = new Pitch('A');
	private Pitch B = new Pitch('B');
	private Pitch C = new Pitch('C');
	private Pitch D = new Pitch('D');
	private Pitch E = new Pitch('E');
	private Pitch F = new Pitch('F');
	private Pitch G = new Pitch('G');
    
    @Test
    public void test() {
        fail("Not yet implemented");
    }
    
    @Test
    public void testPiece1() {
    	try {
    		SequencePlayer player = new SequencePlayer(140, 4);
    		
    		player.addNote(C.toMidiNote(), 0, 4); // TODO: why do these play so slow?
    		player.addNote(C.toMidiNote(), 4, 4);
    		player.addNote(C.toMidiNote(), 8, 3);
    		player.addNote(D.toMidiNote(), 11, 1);
    		player.addNote(E.toMidiNote(), 12, 4);
    		
    		player.addNote(E.toMidiNote(), 16, 3);
    		player.addNote(D.toMidiNote(), 19, 1);
    		player.addNote(E.toMidiNote(), 20, 3);
    		player.addNote(F.toMidiNote(), 23, 1);
    		player.addNote(G.toMidiNote(), 24, 8);
    		
    		//System.err.println(player.toString());
    		
    		//player.play();
    		
    	}
    	catch(MidiUnavailableException mue) {
    		fail(mue.getStackTrace().toString());
    	}
    	catch(InvalidMidiDataException imde) {
    		fail(imde.getStackTrace().toString());
    	}
    }
    

}
