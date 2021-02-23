/*
 * Compile all your grammars using
 *       java -jar ../../../lib/antlr.jar *.g4
 * then Refresh in Eclipse.
 */
grammar Structure;
import Configuration;

root: header EOL* body;

header: (HEADER_LINE EOL)+;
HEADER_LINE: [CKLMQTX] ':' ~[\r\n]+;

body: (BODY_LINE (EOL | EOF))+;
BODY_LINE: ~[\r\n] ~[:] ~[\r\n]+ | '|:' ~[\r\n]+;

EOL: '\n' | '\r' '\n'?;

/* Tell Antlr to ignore spaces around tokens. */
SPACES: [ ] -> skip;