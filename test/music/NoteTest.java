package music;

import static org.junit.Assert.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import music.Note;
import abc.sound.SequencePlayer;
import abc.sound.Pitch;

public class NoteTest {
	//TODO: may want to change Note toString method to only use sharp symbols to match Pitch class, figure out how
	//		to account for various possibilities in tests
	
	/*
	 * Testing strategy for Note
	 * 	Partition the inputs as follows:
	 * 		pitch: natural, sharp, flat
	 * 		duration: 0, integer number of beats, fractional number of beats
	 * 		play at beat: 0, integer value, fractional value with numerator 1, fractional with value numerator n
	 * 		pitch in middle C octave, up one octave, up n octaves, down 1 octave, down n octaves
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
	
	// this test covers natural pitch, fractional duration with numerator 1
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
	
	// this test covers natural pitch, fractional duration with numerator n
		@Test
		public void testNoteNaturalFractionalDurationNumeratorN() {
			try {
				SequencePlayer player1 = new SequencePlayer(100, 4);
				SequencePlayer player2 = new SequencePlayer(100, 4);
				
				Note note = new Note(.75, new Pitch('C'));
				assertTrue(.75 == note.getDuration());
				assertTrue(note.toString().equals("C3/4"));
				
				note.play(player1, 0);
				player2.addNote(new Pitch('C').toMidiNote(), 0, 3);
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
	
	// this test covers sharp pitch, fractional duration with numerator n
		@Test
		public void testNoteSharpFractionalDurationNumeratorN() {
			try {
				SequencePlayer player1 = new SequencePlayer(100, 4);
				SequencePlayer player2 = new SequencePlayer(100, 4);
				
				Note note = new Note(1.5, new Pitch('C').transpose(1));
				assertTrue(1.5 == note.getDuration());
				assertTrue(note.toString().equals("^C6/4") || note.toString().equals("^C3/2"));
				
				note.play(player1, 0);
				player2.addNote(new Pitch('C').transpose(1).toMidiNote(), 0, 6);
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
	
	// this test covers flat pitch, fractional duration with numerator n
		@Test
		public void testNoteFlatFractionalDurationNumeratorN() {
			try {
				SequencePlayer player1 = new SequencePlayer(100, 4);
				SequencePlayer player2 = new SequencePlayer(100, 4);
				
				Note note = new Note(1, new Pitch('C').transpose(-1));
				assertTrue(.75 == note.getDuration());
				assertTrue(note.toString().equals("_C3/4"));
				
				note.play(player1, 0);
				player2.addNote(new Pitch('C').transpose(-1).toMidiNote(), 0, 3);
				assertEquals(player1.toString(), player2.toString());
			} catch(MidiUnavailableException mue) {
				fail(mue.getStackTrace().toString());
			} catch(InvalidMidiDataException imde) {
				fail(imde.getStackTrace().toString());
			}
		}
	
	// this test covers sharp pitch, fractional duration, note up one octave
	@Test
	public void testNoteUpOneOctave() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			
			Note note = new Note(.25, new Pitch('C').transpose(Pitch.OCTAVE + 1));
			assertTrue(.25 == note.getDuration());
			assertTrue(note.toString().equals("^c1/4") || note.toString().equals("^c/4"));
			
			note.play(player1, 0);
			player2.addNote(new Pitch('C').transpose(Pitch.OCTAVE + 1).toMidiNote(), 0, 1);
			assertEquals(player1.toString(), player2.toString());
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers flat pitch, fractional duration, note up n octaves
	@Test
	public void testNoteUpMultipleOctaves() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			
			Note note = new Note(.25, new Pitch('E').transpose(Pitch.OCTAVE*2 - 1));
			assertTrue(.25 == note.getDuration());
			assertTrue(note.toString().equals("_E''1/4") || note.toString().equals("_E''/4"));
			
			note.play(player1, 0);
			player2.addNote(new Pitch('E').transpose(Pitch.OCTAVE*2 - 1).toMidiNote(), 0, 1);
			assertEquals(player1.toString(), player2.toString());
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers natural pitch, integer duration, note down 1 octave
	@Test
	public void testNoteDownOneOctave() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			
			Note note = new Note(2, new Pitch('C').transpose(-1*Pitch.OCTAVE));
			assertTrue(2 == note.getDuration());
			assertTrue(note.toString().equals("C,1/4") || note.toString().equals("C,/4"));
			
			note.play(player1, 0);
			player2.addNote(new Pitch('C').transpose(-1*Pitch.OCTAVE).toMidiNote(), 0, 8);
			assertEquals(player1.toString(), player2.toString());
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers sharp pitch, fractional duration with numerator n, note down n octaves
	@Test
	public void testNoteDownMultipleOctaves() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			
			Note note = new Note(1.25, new Pitch('C').transpose(-2*Pitch.OCTAVE + 1));
			assertTrue(1.25 == note.getDuration());
			assertTrue(note.toString().equals("^C,,5/4"));
			
			note.play(player1, 0);
			player2.addNote(new Pitch('C').transpose(-2*Pitch.OCTAVE + 1).toMidiNote(), 0, 5);
			assertEquals(player1.toString(), player2.toString());
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
}
