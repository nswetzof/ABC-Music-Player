/*
 * Compile all your grammars using
 *       java -jar ../../../lib/antlr.jar *.g4
 * then Refresh in Eclipse.
 */
grammar Structure;
import Configuration;

root: header EOL* BODY;

header: (HEADER_LINE EOL)+;
HEADER_LINE: [CKLMQTX] ':' ~[\r\n]+;

BODY: (~[\r\n] ~[:] | '|:') .*? EOF;

EOL: '\n' | '\r' '\n'?;

/* Tell Antlr to ignore spaces around tokens. */
SPACES: [ ] -> skip;