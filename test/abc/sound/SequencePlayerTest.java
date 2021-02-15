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
    	try {
    		SequencePlayer player = new SequencePlayer(140, 12);
    		
    		player.addNote(C.toMidiNote(), 0, 12);
    		player.addNote(C.toMidiNote(), 12, 24);
    		player.addNote(C.toMidiNote(), 24, 9);
    		player.addNote(D.toMidiNote(), 33, 3);
    		player.addNote(E.toMidiNote(), 36, 12);
    		
    		player.addNote(E.toMidiNote(), 48, 9);
    		player.addNote(D.toMidiNote(), 57, 3);
    		player.addNote(E.toMidiNote(), 60, 9);
    		player.addNote(F.toMidiNote(), 69, 3);
    		player.addNote(G.toMidiNote(), 72, 24);
    		
    		player.addNote(C.transpose(Pitch.OCTAVE).toMidiNote(), 96, 4);
    		player.addNote(C.transpose(Pitch.OCTAVE).toMidiNote(), 100, 4);
    		player.addNote(C.transpose(Pitch.OCTAVE).toMidiNote(), 104, 4);
    		player.addNote(G.toMidiNote(), 108, 4);
    		player.addNote(G.toMidiNote(), 112, 4);
    		player.addNote(G.toMidiNote(), 116, 4);
    		player.addNote(E.toMidiNote(), 120, 4);
    		player.addNote(E.toMidiNote(), 124, 4);
    		player.addNote(E.toMidiNote(), 128, 4);
    		player.addNote(C.toMidiNote(), 132, 4);
    		player.addNote(C.toMidiNote(), 136, 4);
    		player.addNote(C.toMidiNote(), 140, 4);
    		
    		player.addNote(G.toMidiNote(), 144, 9);
    		player.addNote(F.toMidiNote(), 153, 3);
    		player.addNote(E.toMidiNote(), 156, 9);
    		player.addNote(D.toMidiNote(), 165, 3);
    		player.addNote(C.toMidiNote(), 168, 24);
    		
    		//System.err.println(player.toString());
    		
    		player.play();
    		
    	}
    	catch(MidiUnavailableException mue) {
    		fail(mue.getStackTrace().toString());
    	}
    	catch(InvalidMidiDataException imde) {
    		fail(imde.getStackTrace().toString());
    	}
    }
    
//    @Test
//    public void testPiece1() {
//    	try {
//    		SequencePlayer player = new SequencePlayer(140, 12);
//    		
//    		player.addNote(C.toMidiNote(), 0, 12);
//    		player.addNote(C.toMidiNote(), 12, 24);
//    		player.addNote(C.toMidiNote(), 24, 9);
//    		player.addNote(D.toMidiNote(), 33, 3);
//    		player.addNote(E.toMidiNote(), 36, 12);
//    		
//    		player.addNote(E.toMidiNote(), 48, 9);
//    		player.addNote(D.toMidiNote(), 57, 3);
//    		player.addNote(E.toMidiNote(), 60, 9);
//    		player.addNote(F.toMidiNote(), 69, 3);
//    		player.addNote(G.toMidiNote(), 72, 24);
//    		
//    		player.addNote(C.transpose(Pitch.OCTAVE).toMidiNote(), 96, 4);
//    		player.addNote(C.transpose(Pitch.OCTAVE).toMidiNote(), 100, 4);
//    		player.addNote(C.transpose(Pitch.OCTAVE).toMidiNote(), 104, 4);
//    		player.addNote(G.toMidiNote(), 108, 4);
//    		player.addNote(G.toMidiNote(), 112, 4);
//    		player.addNote(G.toMidiNote(), 116, 4);
//    		player.addNote(E.toMidiNote(), 120, 4);
//    		player.addNote(E.toMidiNote(), 124, 4);
//    		player.addNote(E.toMidiNote(), 128, 4);
//    		player.addNote(C.toMidiNote(), 132, 4);
//    		player.addNote(C.toMidiNote(), 136, 4);
//    		player.addNote(C.toMidiNote(), 140, 4);
//    		
//    		player.addNote(G.toMidiNote(), 144, 9);
//    		player.addNote(F.toMidiNote(), 153, 3);
//    		player.addNote(E.toMidiNote(), 156, 9);
//    		player.addNote(D.toMidiNote(), 165, 3);
//    		player.addNote(C.toMidiNote(), 168, 24);
//    		
//    		//System.err.println(player.toString());
//    		
//    		player.play();
//    		
//    	}
//    	catch(MidiUnavailableException mue) {
//    		fail(mue.getStackTrace().toString());
//    	}
//    	catch(InvalidMidiDataException imde) {
//    		fail(imde.getStackTrace().toString());
//    	}
//    }
    

}
