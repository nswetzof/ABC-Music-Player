package Music;

import static org.junit.Assert.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import music.Note;
import abc.sound.SequencePlayer;
import abc.sound.Pitch;

public class NoteTest {
	/*
	 * Testing strategy for Note
	 * 	Partition the inputs as follows:
	 * 		pitch: natural, sharp, flat
	 * 		duration: 0, integer number of beats, fractional number of beats
	 * 		play at beat: 0, integer value, fractional value
	 * 	
	 * 	Verify value returned by getDuration matches expected for each partition of duration
	 * 
	 * 	Verify toString returns note in correct abc format for each partition of duration and pitch
	 * 
	 * 	Verify the SequencePlayer object used when calling the play method matches expected for each
	 * 		partition of duration and pitch.
	 */
	
	// this test covers 0 duration
	@Test
	public void testNoteZeroDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			
			Note note = new Note(0, new Pitch('C'));
			assertTrue(0 == note.getDuration());
			assertEquals("", note.toString());
			
			note.play(player1, 0);
//			player2.addNote(new Pitch('C').toMidiNote(), 0, 0); // don't need this line because note has 0 duration
			assertEquals(player1.toString(), player2.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
		
	}

	// this test covers natural pitch, integer duration
	@Test
	public void testNoteNaturalIntDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			
			Note note = new Note(1, new Pitch('C'));
			assertTrue(1 == note.getDuration());
			assertEquals("C", note.toString());
			
			note.play(player1, 0);
			player2.addNote(new Pitch('C').toMidiNote(), 0, 4);
			assertEquals(player1.toString(), player2.toString());
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers natural pitch, fractional duration
	@Test
	public void testNoteNaturalFractionalDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			
			Note note = new Note(.25, new Pitch('C'));
			assertTrue(.25 == note.getDuration());
			assertTrue(note.toString().equals("C1/4") || note.toString().equals("C/4"));
			
			note.play(player1, 0);
			player2.addNote(new Pitch('C').toMidiNote(), 0, 1);
			assertEquals(player1.toString(), player2.toString());
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers sharp pitch, integer duration
	@Test
	public void testNoteSharpIntDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			
			Note note = new Note(1, new Pitch('C').transpose(1));
			assertTrue(1 == note.getDuration());
			assertEquals("^C", note.toString());
			
			note.play(player1, 0);
			player2.addNote(new Pitch('C').transpose(1).toMidiNote(), 0, 4);
			assertEquals(player1.toString(), player2.toString());
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers sharp pitch, fractional duration
	@Test
	public void testNoteSharpFractionalDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			
			Note note = new Note(.25, new Pitch('C').transpose(1));
			assertTrue(.25 == note.getDuration());
			assertTrue(note.toString().equals("^C1/4") || note.toString().equals("^C/4"));
			
			note.play(player1, 0);
			player2.addNote(new Pitch('C').transpose(1).toMidiNote(), 0, 1);
			assertEquals(player1.toString(), player2.toString());
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers flat pitch, integer duration
	@Test
	public void testNoteFlatIntDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			
			Note note = new Note(1, new Pitch('C').transpose(-1));
			assertTrue(1 == note.getDuration());
			assertEquals("_C", note.toString());
			
			note.play(player1, 0);
			player2.addNote(new Pitch('C').transpose(-1).toMidiNote(), 0, 4);
			assertEquals(player1.toString(), player2.toString());
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers flat pitch, fractional duration
	@Test
	public void testNoteFlatFractionalDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			
			Note note = new Note(1, new Pitch('C').transpose(-1));
			assertTrue(.25 == note.getDuration());
			assertTrue(note.toString().equals("_C1/4") || note.toString().equals("_C/4"));
			
			note.play(player1, 0);
			player2.addNote(new Pitch('C').transpose(-1).toMidiNote(), 0, 1);
			assertEquals(player1.toString(), player2.toString());
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
}
