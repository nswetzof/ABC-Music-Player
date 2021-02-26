/*
 * Compile all your grammars using
 *       java -jar ../../../lib/antlr.jar *.g4
 * then Refresh in Eclipse.
 */
grammar Structure;
import Configuration;

root: header EOL* BODY;

header: field_number comment* field_title other_fields* field_key;

field_number: 'X:' DIGIT+ EOL;
comment: '%' TEXT EOL;
field_title: 'T:' TEXT (DIGIT | TEXT)* EOL;
other_fields: field_composer | field_default_length | field_meter | field_tempo | field_voice | comment;
field_composer: 'C:' TEXT EOL;
field_default_length: 'L:' note_length_strict EOL;
field_meter: 'M:' ('C' | 'C|' | meter) EOL;
field_tempo: 'Q:' tempo EOL;
field_voice: 'V:' TEXT EOL;
field_key: 'K:' key EOL;

note_length_strict: DIGIT+ '/' DIGIT+;
meter: meter_fraction;
meter_fraction: DIGIT+ '/' DIGIT+;
tempo: meter_fraction '=' DIGIT+;

key: keynote MODE_MINOR?;
keynote: BASENOTE KEY_ACCIDENTAL?;

BODY: ([a-zA-Z_^|(] ~[:\r\n] | '|:' | '||:') .*? EOF;

DIGIT: [0-9];
TEXT: [a-zA-Z][. a-zA-Z]+;
KEY_ACCIDENTAL: [#b];
MODE_MINOR: 'm';
BASENOTE: [A-Fa-f];
EOL: '\n' | '\r' '\n'?;

/* Tell Antlr to ignore spaces around tokens. */
SPACES: [ ] -> skip;