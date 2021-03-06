package music;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class SongTest {
	
	/*
	 * Testing strategy for Song:
	 * 		Run Parse on each file in sample_abc;
	 * 		verify header information matches expected based on file contents;
	 * 		verify SequencePlayer derived from generated object is correct based on the abc specification 
	 */

	@Test
	public void testAbc_song() {
		try {
			Song song = Song.parse("sample_abc/abc_song.abc");
			
			assertEquals(1, song.getIndex());
			assertEquals("Alphabet Song", song.getTitle());
			assertEquals("Traditional Kid's Song", song.getComposer());
			
			assertTrue(song.getLength() == 1/4);
			
			assertTrue(song.getMeter().first() == 4);
			assertTrue(song.getMeter().second() == 4);
			
			assertTrue(song.getTempo().first() == 1/4);
			assertTrue(song.getTempo().second() == 100);
			
			assertEquals("D", song.getKey());
			
			assertTrue(1 == song.getVoiceNames().size());
			
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	}
	
	@Test
	public void testFur_elise() {
		try {
			Song song = Song.parse("sample_abc/fur_elise.abc");
			
			assertEquals(1, song.getIndex());
			assertEquals("Bagatelle No.25 in A, WoO.59", song.getTitle());
			assertEquals("Ludwig van Beethoven", song.getComposer());
			
			assertTrue(song.getLength() == 1/16);
			
			assertTrue(song.getMeter().first() == 3);
			assertTrue(song.getMeter().second() == 8);
			
			assertTrue(song.getTempo().first() == 1/8);
			assertTrue(song.getTempo().second() == 140);
			
			assertEquals("D", song.getKey());
			
			assertTrue(2 == song.getVoiceNames().size());
			assertTrue(song.getVoiceNames().contains("1"));
			assertTrue(song.getVoiceNames().contains("2"));
			
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	}
	
	@Test
	public void testInvention() {
		try {
			Song song = Song.parse("sample_abc/invention.abc");
			
			assertEquals(1868, song.getIndex());
			assertEquals("Invention no. 1", song.getTitle());
			assertEquals("Johann Sebastian Bach", song.getComposer());
			
			assertTrue(song.getLength() == 1/8);
			
			assertTrue(song.getMeter().first() == 4);
			assertTrue(song.getMeter().second() == 4);
			
			assertTrue(song.getTempo().first() == 1/4);
			assertTrue(song.getTempo().second() == 70);
			
			assertEquals("C", song.getKey());
			
			assertTrue(2 == song.getVoiceNames().size());
			assertTrue(song.getVoiceNames().contains("1"));
			assertTrue(song.getVoiceNames().contains("2"));
			
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	}
	
	@Test
	public void testLittle_night_music() {
		try {
			Song song = Song.parse("sample_abc/little_night_music.abc");
			
			assertEquals(1, song.getIndex());
			assertEquals("Little Night Music Mvt. 1", song.getTitle());
			assertEquals("Wolfgang Amadeus Mozart", song.getComposer());
			
			assertTrue(song.getLength() == 1/8);
			
			assertTrue(song.getMeter().first() == 4);
			assertTrue(song.getMeter().second() == 4);
			
			assertTrue(song.getTempo().first() == 1/4);
			assertTrue(song.getTempo().second() == 140);
			
			assertEquals("G", song.getKey());
			
			assertTrue(1 == song.getVoiceNames().size());
			
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	}
	
	@Test
	public void testPaddy() {
		try {
			Song song = Song.parse("sample_abc/paddy.abc");
			
			assertEquals(1, song.getIndex());
			assertEquals("Paddy O'Rafferty", song.getTitle());
			assertEquals("Trad.", song.getComposer());
			
			assertTrue(song.getLength() == 1/8);
			
			assertTrue(song.getMeter().first() == 6);
			assertTrue(song.getMeter().second() == 8);
			
			assertTrue(song.getTempo().first() == 1/8);
			assertTrue(song.getTempo().second() == 200);
			
			assertEquals("D", song.getKey());
			
			assertTrue(1 == song.getVoiceNames().size());
			
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	}
	
	@Test
	public void testPiece1() {
		try {
			Song song = Song.parse("sample_abc/piece1.abc");
			
			assertEquals(1, song.getIndex());
			assertEquals("Piece No.1", song.getTitle());
			assertEquals("Unknown", song.getComposer());
			
			assertTrue(song.getLength() == 1/4);
			
			assertTrue(song.getMeter().first() == 4);
			assertTrue(song.getMeter().second() == 4);
			
			assertTrue(song.getTempo().first() == 1/4);
			assertTrue(song.getTempo().second() == 140);
			
			assertEquals("C", song.getKey());
			
			assertTrue(1 == song.getVoiceNames().size());
			
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	}
	
	@Test
	public void testPiece2() {
		try {
			Song song = Song.parse("piece2/invention.abc");
			
			assertEquals(2, song.getIndex());
			assertEquals("Piece No.2", song.getTitle());
			assertEquals("Unknown", song.getComposer());
			
			assertTrue(song.getLength() == 1/4);
			
			assertTrue(song.getMeter().first() == 4);
			assertTrue(song.getMeter().second() == 4);
			
			assertTrue(song.getTempo().first() == 1/4);
			assertTrue(song.getTempo().second() == 200);
			
			assertEquals("C", song.getKey());
			
			assertTrue(1 == song.getVoiceNames().size());
			
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	}
	
	@Test
	public void testPrelude() {
		try {
			Song song = Song.parse("sample_abc/prelude.abc");
			
			assertEquals(8628, song.getIndex());
			assertEquals("Prelude BWV 846 no. 1", song.getTitle());
			assertEquals("Johann Sebastian Bach", song.getComposer());
			
			assertTrue(song.getLength() == 1/16);
			
			assertTrue(song.getMeter().first() == 4);
			assertTrue(song.getMeter().second() == 4);
			
			assertTrue(song.getTempo().first() == 1/4);
			assertTrue(song.getTempo().second() == 70);
			
			assertEquals("C", song.getKey());
			
			assertTrue(3 == song.getVoiceNames().size());
			assertTrue(song.getVoiceNames().contains("1"));
			assertTrue(song.getVoiceNames().contains("2"));
			assertTrue(song.getVoiceNames().contains("3"));
			
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	}
	
	@Test
	public void testSample1() {
		try {
			Song song = Song.parse("sample_abc/sample1.abc");
			
			assertEquals(1, song.getIndex());
			assertEquals("sample 1", song.getTitle());
			assertEquals("Unknown", song.getComposer());
			
			assertTrue(song.getLength() == 1/8);
			
			assertTrue(song.getMeter().first() == 4);
			assertTrue(song.getMeter().second() == 4);
			
			assertTrue(song.getTempo().first() == 1/8);
			assertTrue(song.getTempo().second() == 100);
			
			assertEquals("C", song.getKey());
			
			assertTrue(1 == song.getVoiceNames().size());
			
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	}
	
	@Test
	public void testSample2() {
		try {
			Song song = Song.parse("sample_abc/sample2.abc");
			
			assertEquals(8, song.getIndex());
			assertEquals("Chord", song.getTitle());
			assertEquals("Unknown", song.getComposer());
			assertTrue(song.getLength() == 1/8);
			
			assertTrue(song.getMeter().first() == 4);
			assertTrue(song.getMeter().second() == 4);
			
			assertTrue(song.getTempo().first() == 1/4);
			assertTrue(song.getTempo().second() == 100);
			
			assertEquals("C", song.getKey());
			
			assertTrue(1 == song.getVoiceNames().size());
			
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	}
	
	@Test
	public void testSample3() {
		try {
			Song song = Song.parse("sample_abc/sample.abc");
			
			assertEquals(1, song.getIndex());
			assertEquals("Voices", song.getTitle());
			assertEquals("Unknown", song.getComposer());
			assertTrue(song.getLength() == 1/8);
			
			assertTrue(song.getMeter().first() == 4);
			assertTrue(song.getMeter().second() == 4);
			
			assertTrue(song.getTempo().first() == 1/4);
			assertTrue(song.getTempo().second() == 100);
			
			assertEquals("Cm", song.getKey());
			
			assertTrue(3 == song.getVoiceNames().size());
			assertTrue(song.getVoiceNames().contains("1"));
			assertTrue(song.getVoiceNames().contains("2"));
			assertTrue(song.getVoiceNames().contains("3"));
			
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	}
	
	@Test
	public void testScale() {
		try {
			Song song = Song.parse("sample_abc/scale.abc");
			
			assertEquals(1868, song.getIndex());
			assertEquals("Simple scale", song.getTitle());
			assertEquals("Unknown", song.getComposer());
			assertTrue(song.getLength() == 1/4);
			
			assertTrue(song.getMeter().first() == 4);
			assertTrue(song.getMeter().second() == 4);
			
			assertTrue(song.getTempo().first() == 1/4);
			assertTrue(song.getTempo().second() == 120);
			
			assertEquals("C", song.getKey());
			
			assertTrue(1 == song.getVoiceNames().size());
			
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	}
	
	@Test
	public void testWaxies_dargle() {
		try {
			Song song = Song.parse("sample_abc/waxies_dargle.abc");
			
			assertEquals(2167, song.getIndex());
			assertEquals("Waxie's Dargle", song.getTitle());
			assertEquals("Unknown", song.getComposer());
			assertTrue(song.getLength() == 1/8);
			
			assertTrue(song.getMeter().first() == 4);
			assertTrue(song.getMeter().second() == 4);
			
			assertTrue(song.getTempo().first() == 1/4);
			assertTrue(song.getTempo().second() == 180);
			
			assertEquals("G", song.getKey());
			
			assertTrue(1 == song.getVoiceNames().size());
			
		} catch(IOException ioe) {
			fail(ioe.getMessage());
		}
	}
}
