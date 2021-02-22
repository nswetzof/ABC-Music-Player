/*
 * Compile all your grammars using
 *       java -jar ../../../lib/antlr.jar *.g4
 * then Refresh in Eclipse.
 */
grammar Structure;
import Configuration;

root: header EOL* body EOF

header: field-number comment* field-title other-fields* field-key

field-number: 'X:' DIGIT+ EOL
comment: '%' TEXT EOL
field-title: 'T:' TEXT EOL
other-fields: field-composer | field-default-length | field-meter | field-tempo | field-voice | comment
field-composer: 'C:' TEXT EOL
field-default-length: 'L:' note-length-strict EOL
field-meter: 'M:' meter EOL
field-tempo: 'Q:' tempo EOL
field-voice: 'V:' TEXT EOL
field-key: 'K:' key EOL

note-length-strict: DIGIT+ '/' DIGIT+
meter: 'C' | 'C|' | meter-fraction
meter-fraction: DIGIT+ '/' DIGIT+
tempo: meter-fraction "=" DIGIT+

key: keynote MODE-MINOR?
keynote: BASENOTE KEY-ACCIDENTAL?

;whitespace: \b* EOL

body: line (EOL line)* EOF

line: [\w\b[]|:]
;line: ([^_] BASENOTE ('\''* | ','*) meter-fraction?)+ | [\[]:]

DIGIT: \d
TEXT: [a-zA-Z][ a-zA-Z]*
KEY-ACCIDENTAL: [#b]
MODE-MINOR: 'm'
BASENOTE: [A-Fa-f]
EOL: \n | \r\n?

PLUS: '+';

/* Tell Antlr to ignore spaces around tokens. */
SPACES: [ ] -> skip;