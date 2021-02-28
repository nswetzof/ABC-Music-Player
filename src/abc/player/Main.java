package abc.player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import abc.parser.StructureLexer;
import abc.parser.StructureListener;
import abc.parser.StructureParser;

import abc.parser.MusicLexer;
import abc.parser.MusicListener;
import abc.parser.MusicParser;

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
        // YOUR CODE HERE
    }

    public static void main(String[] args) {
    	try {
	    	FileInputStream in = new FileInputStream("sample_abc/piece2.abc");
	    	CharStream stream = new ANTLRInputStream(in);
	    	
	    	StructureLexer lexer = new StructureLexer(stream);
	    	TokenStream tokens = new CommonTokenStream(lexer);
	    	
	    	StructureParser parser = new StructureParser(tokens);
	    	ParseTree tree = parser.root();
	    	
	    	System.err.println(tree.toStringTree(parser));
	    	
	    	Trees.inspect(tree, parser);
	    	
    	} catch(FileNotFoundException fnfe) {
    		System.err.println(fnfe.getMessage());
    		System.exit(0);
    	} catch(IOException ioe) {
    		System.err.println(ioe.getMessage());
    		System.exit(0);
    	}
    	
        // CALL play() HERE USING ARGS
    }
}
