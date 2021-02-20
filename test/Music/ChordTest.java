package Music;

import static org.junit.Assert.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

import abc.sound.SequencePlayer;
import abc.sound.Pitch;
import music.Note;
import music.Chord;

public class ChordTest {
	
	/*
	 * Testing strategy for chord:
	 * 	Partition the inputs as follows:
	 * 		number of notes: 1, n
	 * 		notes have same length, different length
	 * 
	 * 	Verify values returned by getDuration match expected for each partition of input
	 * 
	 * 	Verify values returned by toString match expected for each partition of input
	 * 
	 * 	Verify the SequencePlayer object used when calling the play method matches expected for each
	 * 		partition of duration and pitch.
	 */
	
	// this test covers 1 note in chord
	@Test
	public void testChordOneNote() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			
			List<Note> notes = new ArrayList<Note>();
			Note note1 = new Note(1, new Pitch('C'));
			notes.add(note1);
			Chord chord = new Chord(notes);
			
			assertTrue(chord.getDuration() == 1);
			
			assertEquals(chord.toString(), "[C]");
			
			chord.play(player1, 0);
			player2.addNote(new Pitch('C').toMidiNote(), 0, 4);
			assertEquals(player1.toString(), player2.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}

	// this test covers multiple notes in chord, notes have same length
	@Test
	public void testChordMultipleNotesSameLength() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			
			List<Note> notes = new ArrayList<Note>();
			Note note1 = new Note(1, new Pitch('C'));
			Note note2 = new Note(1, new Pitch('E'));
			Note note3 = new Note(1, new Pitch('G'));
			
			notes.add(note1);
			notes.add(note2);
			notes.add(note3);
			Chord chord = new Chord(notes);
			
			assertTrue(chord.getDuration() == 1);
			
			assertEquals(chord.toString(), "[CEG]");
			
			chord.play(player1, 0);
			player2.addNote(new Pitch('C').toMidiNote(), 0, 4);
			player2.addNote(new Pitch('E').toMidiNote(), 0, 4);
			player2.addNote(new Pitch('G').toMidiNote(), 0, 4);
			assertEquals(player1.toString(), player2.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
	
	// this test covers multiple notes in chord, notes have differing lengths
	@Test
	public void testChordMultipleNotesDifferentLength() {
		try {
			SequencePlayer player1 = new SequencePlayer(100, 4);
			SequencePlayer player2 = new SequencePlayer(100, 4);
			
			List<Note> notes = new ArrayList<Note>();
			Note note1 = new Note(1, new Pitch('C'));
			Note note2 = new Note(.5, new Pitch('E').transpose(-1));
			Note note3 = new Note(2, new Pitch('G'));
			
			notes.add(note1);
			notes.add(note2);
			notes.add(note3);
			Chord chord = new Chord(notes);
			
			assertTrue(chord.getDuration() == 1);
			
			assertTrue(chord.toString().equals("[C_E1/2G2]") || chord.toString().equals("[C_E/2G2]"));
			
			chord.play(player1, 0);
			player2.addNote(new Pitch('C').toMidiNote(), 0, 4);
			player2.addNote(new Pitch('E').transpose(-1).toMidiNote(), 0, 2);
			player2.addNote(new Pitch('G').toMidiNote(), 0, 8);
			assertEquals(player1.toString(), player2.toString());
			
		} catch(MidiUnavailableException mue) {
			fail(mue.getStackTrace().toString());
		} catch(InvalidMidiDataException imde) {
			fail(imde.getStackTrace().toString());
		}
	}
}
