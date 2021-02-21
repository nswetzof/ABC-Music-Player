package music;

import static org.junit.Assert.*;

import java.util.Arrays;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import abc.sound.SequencePlayer;
import abc.sound.Pitch;

public class ConcatTest {
	private final int ticksPerBeat = 4;
	
	/*
	 * Testing strategy for Concat(first, second):
	 * 	Partition the inputs as follows:
	 * 		Type of first: Rest, Note, Chord, Concat
	 * 		Type of second: Rest, Note, Chord, Concat
	 * 		first has duration 0, m
	 * 		second has duration 0, n
	 * 		start at tick: 0, p
	 * 
	 * 	Verify output matches expected for duration, toString and play methods
	 */

	// this test covers first of type Rest, second of type Rest, first has duration 0, second has duration 0,
	// start at tick 0
	@Test
	public void testConcatRestRestNoDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Music first = new Rest(0, ticksPerBeat);
			Music second = new Rest(0, ticksPerBeat);
			
			Concat concat = new Concat(first, second);
			
			assertTrue(concat.getDuration() == 0);
			assertTrue(concat.toString().equals(""));
			
			concat.play(player1, 0);
			
			assertEquals(player2.toString(), player1.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers first of type Rest, second of type Rest, first has duration m, second has duration 0,
	// start at tick p
	@Test
	public void testConcatRestRestFirstWithDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Music first = new Rest(2, ticksPerBeat);
			Music second = new Rest(0, ticksPerBeat);
			
			Concat concat = new Concat(first, second);
			
			assertTrue(concat.getDuration() == 2);
			assertTrue(concat.toString().equals(".1/2"));
			
			concat.play(player1, 3);
			
			assertEquals(player2.toString(), player1.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers first of type Rest, second of type Rest, first has duration m, second has duration n,
	// start at tick 0
	@Test
	public void testConcatRestRestBothWithDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Music first = new Rest(2, ticksPerBeat);
			Music second = new Rest(5, ticksPerBeat);
			
			Concat concat = new Concat(first, second);
			
			assertTrue(concat.getDuration() == 7);
			assertTrue(concat.toString().equals(".1/2 .5/4"));
			
			concat.play(player1, 0);
			
			assertEquals(player2.toString(), player1.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers first of type Rest, second of type Note, first has duration m, second has duration 0
	// start at tick p
	@Test
	public void testConcatRestNoteFirstWithDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Music first = new Rest(2, ticksPerBeat);
			Music second = new Note(0, ticksPerBeat, new Pitch('C'));
			
			Concat concat = new Concat(first, second);
			
			assertTrue(concat.getDuration() == 2);
			assertTrue(concat.toString().equals(".1/2"));
			
			concat.play(player1, 3);
			
			player2.addNote(new Pitch('C').toMidiNote(), 5, 0);
			
			assertEquals(player2.toString(), player1.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers first of type Rest, second of type Note, first has duration m, second has duration n,
	// start at tick 0
	@Test
	public void testConcatRestNoteBothWithDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Music first = new Rest(2, ticksPerBeat);
			Music second = new Note(5, ticksPerBeat, new Pitch('C'));
			
			Concat concat = new Concat(first, second);
			
			assertTrue(concat.getDuration() == 7);
			assertTrue(concat.toString().equals(".1/2 C5/4"));
			
			concat.play(player1, 0);
			
			player2.addNote(new Pitch('C').toMidiNote(), 2, 5);
			
			assertEquals(player2.toString(), player1.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers first of type Note, second of type Note, first has duration 0, second has duration 0,
	// start at tick 0
	@Test
	public void testConcatNoteNoteNoDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Music first = new Note(0, ticksPerBeat, new Pitch('A'));
			Music second = new Note(0, ticksPerBeat, new Pitch('C'));
			
			Concat concat = new Concat(first, second);
			
			assertTrue(concat.getDuration() == 0);
			assertTrue(concat.toString().equals(""));
			
			concat.play(player1, 0);
			
			player2.addNote(new Pitch('A').toMidiNote(), 0, 0);
			player2.addNote(new Pitch('C').toMidiNote(), 0, 0);
			
			assertEquals(player2.toString(), player1.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers first of type Note, second of type Note, first has duration m, second has duration n
	// start at tick 0
	@Test
	public void testConcatNoteNoteBothWithDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Music first = new Note(2, ticksPerBeat, new Pitch('A'));
			Music second = new Note(5, ticksPerBeat, new Pitch('C'));
			
			Concat concat = new Concat(first, second);
			
			assertTrue(concat.getDuration() == 7);
			assertTrue(concat.toString().equals("A1/2 C5/4"));
			
			concat.play(player1, 0);
			
			player2.addNote(new Pitch('A').toMidiNote(), 0, 2);
			player2.addNote(new Pitch('C').toMidiNote(), 2, 5);
			
			assertEquals(player2.toString(), player1.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers first of type Note, second of type Chord, first has duration m, second has duration n
	// start at tick 0
	@Test
	public void testConcatNoteChordBothWithDurations() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Music first = new Note(2, ticksPerBeat, new Pitch('A'));
			
			Note note1 = new Note(5, ticksPerBeat, new Pitch('C'));
			Note note2 = new Note(4, ticksPerBeat, new Pitch('E'));
			Note note3 = new Note(6, ticksPerBeat, new Pitch('G'));
			
			Music second = new Chord(Arrays.asList(note1, note2, note3));
			
			Concat concat = new Concat(first, second);
			
			assertTrue(concat.getDuration() == 7);
			assertTrue(concat.toString().equals("A1/2 [C5/4EG3/2]"));
			
			concat.play(player1, 0);
			
			player2.addNote(new Pitch('A').toMidiNote(), 0, 2);
			player2.addNote(new Pitch('C').toMidiNote(), 2, 5);
			player2.addNote(new Pitch('E').toMidiNote(), 2, 4);
			player2.addNote(new Pitch('G').toMidiNote(), 2, 6);
			
			assertEquals(player2.toString(), player1.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers first of type Chord, second of type Note, first has duration m, second has duration n,
	// start at tick p
	@Test
	public void testConcatChordNoteBothWithDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note1 = new Note(5, ticksPerBeat, new Pitch('C'));
			Note note2 = new Note(4, ticksPerBeat, new Pitch('E'));
			Note note3 = new Note(6, ticksPerBeat, new Pitch('G'));
			
			Music first = new Chord(Arrays.asList(note1, note2, note3));

			Music second = new Note(2, ticksPerBeat, new Pitch('A'));
			
			Concat concat = new Concat(first, second);
			
			assertTrue(concat.getDuration() == 7);
			assertTrue(concat.toString().equals("[C5/4EG3/2] A1/2"));
			
			concat.play(player1, 3);
			
			player2.addNote(new Pitch('C').toMidiNote(), 3, 5);
			player2.addNote(new Pitch('E').toMidiNote(), 3, 4);
			player2.addNote(new Pitch('G').toMidiNote(), 3, 6);
			player2.addNote(new Pitch('A').toMidiNote(), 8, 2);
			
			assertEquals(player2.toString(), player1.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}

	// this test covers first of type Concat, second of type Rest, first has duration 0, second has duration n,
	// start at tick p
	@Test
	public void testConcatConcatRestSecondWithDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note1 = new Note(0, ticksPerBeat, new Pitch('A'));
			Note note2 = new Note(0, ticksPerBeat, new Pitch('C'));
			
			Music first = new Concat(note1, note2);

			Music second = new Rest(2, ticksPerBeat);
			
			Concat concat = new Concat(first, second);
			
			assertTrue(concat.getDuration() == 2);
			assertTrue(concat.toString().equals(".1/2"));
			
			concat.play(player1, 3);
			
			player2.addNote(new Pitch('A').toMidiNote(), 3, 0);
			player2.addNote(new Pitch('C').toMidiNote(), 3, 0);
			
			assertEquals(player2.toString(), player1.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers first of type Concat, second of type Note, first has duration m, second has duration n,
	// start at tick 0
	@Test
	public void testConcatConcatNoteBothWithDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note1 = new Note(4, ticksPerBeat, new Pitch('A'));
			Note note2 = new Note(4, ticksPerBeat, new Pitch('C'));
			
			Music first = new Concat(note1, note2);

			Music second = new Note(2, ticksPerBeat, new Pitch('C'));
			
			Concat concat = new Concat(first, second);
			
			assertTrue(concat.getDuration() == 10);
			assertTrue(concat.toString().equals("A C C1/2"));
			
			concat.play(player1, 0);
			
			player2.addNote(new Pitch('A').toMidiNote(), 0, 4);
			player2.addNote(new Pitch('C').toMidiNote(), 4, 4);
			player2.addNote(new Pitch('C').toMidiNote(), 8, 2);
			
			assertEquals(player2.toString(), player1.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers first of type Concat, second of type Concat, first has duration m, second has duration 0,
	// start at tick 0
	@Test
	public void testConcatBothConcatFirstWithDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note1 = new Note(4, ticksPerBeat, new Pitch('A'));
			Note note2 = new Note(4, ticksPerBeat, new Pitch('C'));
			
			Music first = new Concat(note1, note2);
			
			Rest rest1 = new Rest(0, ticksPerBeat);
			Note note3 = new Note(0, ticksPerBeat, new Pitch('G'));

			Music second = new Concat(rest1, note3);
			
			Concat concat = new Concat(first, second);
			
			assertTrue(concat.getDuration() == 8);
			assertTrue(concat.toString().equals("A C"));
			
			concat.play(player1, 0);
			
			player2.addNote(new Pitch('A').toMidiNote(), 0, 4);
			player2.addNote(new Pitch('C').toMidiNote(), 4, 4);
			player2.addNote(new Pitch('G').toMidiNote(), 8, 0);
			
			assertEquals(player2.toString(), player1.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers first of type Concat, second of type Concat, first has duration m, second has duration n,
	// start at tick p
	@Test
	public void testConcatBothConcatBothWithDuration() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, ticksPerBeat);
			SequencePlayer player2 = new SequencePlayer(100, ticksPerBeat);
			
			Note note1 = new Note(4, ticksPerBeat, new Pitch('A'));
			Note note2 = new Note(4, ticksPerBeat, new Pitch('C'));
			
			Music first = new Concat(note1, note2);
			
			Rest rest1 = new Rest(3, ticksPerBeat);
			Note note3 = new Note(1, ticksPerBeat, new Pitch('G'));

			Music second = new Concat(rest1, note3);
			
			Concat concat = new Concat(first, second);
			
			assertTrue(concat.getDuration() == 12);
			assertTrue(concat.toString().equals("A C .3/4 G1/4"));
			
			concat.play(player1, 3);
			
			player2.addNote(new Pitch('A').toMidiNote(), 3, 4);
			player2.addNote(new Pitch('C').toMidiNote(), 7, 4);
			player2.addNote(new Pitch('G').toMidiNote(), 14, 1);
			
			assertEquals(player2.toString(), player1.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
}
