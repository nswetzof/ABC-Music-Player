package abc.player;

import java.io.FileNotFoundException;
import java.io.IOException;

import music.Song;

/**
 * Main entry point of your application.
 */
public class Main {

    /**
     * Plays the input file using Java MIDI API and displays
     * header information to the standard output stream.
     * 
     * (Your code should not exit the application abnormally using
     * System.exit().)
     * 
     * @param file the name of input abc file
     */
    public static void play(String file) {
        try {
	    	Song s = Song.parse(file);
	    	s.play();
	    	
	    	s.printHeader();
	    	
        } catch(FileNotFoundException fnfe) {
    		System.err.println(fnfe.getMessage());
    		System.exit(0);
    	} catch(IOException ioe) {
    		System.err.println(ioe.getMessage());
    		System.exit(0);
    	}
    }

    public static void main(String[] args) {
    	play(args[0]);
    }
}
