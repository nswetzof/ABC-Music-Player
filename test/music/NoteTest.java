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
	 * 		duration: 0, integer number of beats, fractional number of beats: with numerator 1, with numerator n
	 * 		play at tick: 0, 1, n
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
			final int ticksPerBeat = 4;
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note = new Note(0, ticksPerBeat, new Pitch('C'));
			assertTrue(0 == note.getDuration());
			assertEquals("", note.toString());
			
			note.play(player1, 0);
			assertEquals(player1.toString(), player2.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
		
	}

	// this test covers natural pitch, duration is integer number of beats
	@Test
	public void testNoteNaturalIntDuration() {
		try {
			final int ticksPerBeat = 4;
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note = new Note(4, ticksPerBeat, new Pitch('C'));
			assertTrue(4 == note.getDuration());
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
	
	// this test covers natural pitch, duration is fractional number of beats with numerator 1
	@Test
	public void testNoteNaturalFractionalDuration() {
		try {
			final int ticksPerBeat = 4;
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note = new Note(1, ticksPerBeat, new Pitch('C'));
			assertTrue(1 == note.getDuration());
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
	
	// this test covers natural pitch, duration is fractional number of beats with numerator n
		@Test
		public void testNoteNaturalFractionalDurationNumeratorN() {
			try {
				final int ticksPerBeat = 4;
				SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
				SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
				
				Note note = new Note(3, ticksPerBeat, new Pitch('C'));
				assertTrue(3 == note.getDuration());
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
	
	// this test covers sharp pitch, duration is integer number of beats
	@Test
	public void testNoteSharpIntDuration() {
		try {
			final int ticksPerBeat = 4;
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note = new Note(4, ticksPerBeat, new Pitch('C').transpose(1));
			assertTrue(4 == note.getDuration());
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
	
	// this test covers sharp pitch, duration is fractional number of beats with numerator 1
	@Test
	public void testNoteSharpFractionalDuration() {
		try {
			final int ticksPerBeat = 4;
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note = new Note(1, ticksPerBeat, new Pitch('C').transpose(1));
			assertTrue(1 == note.getDuration());
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
	
	// this test covers sharp pitch, duration is fractional number of beats with numerator n
		@Test
		public void testNoteSharpFractionalDurationNumeratorN() {
			try {
				final int ticksPerBeat = 4;
				SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
				SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
				
				Note note = new Note(6, ticksPerBeat, new Pitch('C').transpose(1));
				assertTrue(6 == note.getDuration());
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
	
	// this test covers flat pitch, duration is integer number of beats
	@Test
	public void testNoteFlatIntDuration() {
		try {
			final int ticksPerBeat = 4;
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note = new Note(4, ticksPerBeat, new Pitch('E').transpose(-1));
			assertTrue(4 == note.getDuration());
			assertEquals("_E", note.toString());
			
			note.play(player1, 0);
			player2.addNote(new Pitch('E').transpose(-1).toMidiNote(), 0, 4);
			assertEquals(player1.toString(), player2.toString());
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers flat pitch, duration is fractional number of beats with numerator 1
	@Test
	public void testNoteFlatFractionalDuration() {
		try {
			final int ticksPerBeat = 4;
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note = new Note(4, ticksPerBeat, new Pitch('C').transpose(-1));
			assertTrue(1 == note.getDuration());
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
	
	// this test covers flat pitch, duration is fractional number of beats with numerator n
		@Test
		public void testNoteFlatFractionalDurationNumeratorN() {
			try {
				final int ticksPerBeat = 4;
				SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
				SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
				
				Note note = new Note(3, ticksPerBeat, new Pitch('E').transpose(-1));
				assertTrue(3 == note.getDuration());
				assertTrue(note.toString().equals("_E3/4"));
				
				note.play(player1, 0);
				player2.addNote(new Pitch('E').transpose(-1).toMidiNote(), 0, 3);
				assertEquals(player1.toString(), player2.toString());
			} catch(MidiUnavailableException mue) {
				fail(mue.getStackTrace().toString());
			} catch(InvalidMidiDataException imde) {
				fail(imde.getStackTrace().toString());
			}
		}
	
	// this test covers sharp pitch, duration is fractional number of beats with numerator 1, note up one octave
	@Test
	public void testNoteUpOneOctave() {
		try {
			final int ticksPerBeat = 4;
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note = new Note(1, ticksPerBeat, new Pitch('C').transpose(Pitch.OCTAVE + 1));
			assertTrue(1 == note.getDuration());
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
	
	// this test covers flat pitch, duration is fractional number of beats with numerator 1, note up n octaves
	@Test
	public void testNoteUpMultipleOctaves() {
		try {
			final int ticksPerBeat = 4;
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note = new Note(1, ticksPerBeat, new Pitch('E').transpose(Pitch.OCTAVE*2 - 1));
			assertTrue(1 == note.getDuration());
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
	
	// this test covers natural pitch, duration is integer number of beats, note down 1 octave
	@Test
	public void testNoteDownOneOctave() {
		try {
			final int ticksPerBeat = 4;
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note = new Note(8, ticksPerBeat, new Pitch('C').transpose(-1*Pitch.OCTAVE));
			assertTrue(8 == note.getDuration());
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
	
	// this test covers sharp pitch, duration is fractional number of beats with numerator n, note down n octaves
	@Test
	public void testNoteDownMultipleOctaves() {
		try {
			final int ticksPerBeat = 4;
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note = new Note(5, ticksPerBeat, new Pitch('C').transpose(-2*Pitch.OCTAVE + 1));
			assertTrue(5 == note.getDuration());
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
