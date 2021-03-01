/*
 * Compile all your grammars using
 *       java -jar ../../../lib/antlr.jar *.g4
 * then Refresh in Eclipse.
 */
grammar Structure;
import Configuration;

root: header EOL* body;

header: field_number comment* FIELD_TITLE other_fields* field_key;

field_number: 'X:' DIGIT+ EOL;
comment: '%' .+? EOL;
FIELD_TITLE: 'T:' .+? EOL;
other_fields: FIELD_COMPOSER | field_default_length | field_meter | field_tempo | field_voice | comment;
FIELD_COMPOSER: 'C:' [a-zA-Z .]+ EOL;
field_default_length: 'L:' note_length_strict EOL;
field_meter: ('M:C' | 'M:C|' | 'M:' meter) EOL;
field_tempo: 'Q:' tempo EOL;
field_voice: 'V:' ~EOL+? EOL;
field_key: 'K:' key EOL;

note_length_strict: DIGIT+ '/' DIGIT+;
meter: meter_fraction;
meter_fraction: DIGIT+ '/' DIGIT+;
tempo: meter_fraction '=' DIGIT+;

key: keynote MODE_MINOR?;
keynote: BASENOTE KEY_ACCIDENTAL?;

body: (BARLINE | '(' | ACCIDENTAL | 'z' | '[' | BASENOTE ~':' | 'V:') (. | ']')*? EOF;
BARLINE: '|' | '||' | '[|' | '|]' | ':|' | '|:';

ACCIDENTAL: '^' | '^^' | '_' | '__' | '=';

KEY_ACCIDENTAL: [#b];
MODE_MINOR: 'm';
DIGIT: [0-9];
BASENOTE: [A-Ga-g];
EOL: '\n' | '\r''\n'?;

/* Tell Antlr to ignore spaces around tokens.
SPACES: [ ] -> skip;