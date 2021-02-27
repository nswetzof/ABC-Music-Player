/*
 * Compile all your grammars using
 *       java -jar ../../../lib/antlr.jar *.g4
 * then Refresh in Eclipse.
 */
grammar Music;
import Configuration;

root: header EOL* body;

header: field_number COMMENT* FIELD_TITLE other_fields* field_key;

field_number: 'X:' DIGIT+ EOL;
COMMENT: '%' ~[\r\n]+? EOL;
FIELD_TITLE: 'T:' ~[\r\n]+? EOL;
other_fields: FIELD_COMPOSER | field_default_length | field_meter | field_tempo | FIELD_VOICE | COMMENT;
FIELD_COMPOSER: 'C:' [a-zA-Z .]+? EOL;
field_default_length: 'L:' note_length_strict EOL;
field_meter: 'M' (':C' | ':C|' | ':' meter) EOL;
field_tempo: 'Q:' tempo EOL;
FIELD_VOICE: 'V:' [a-zA-Z ]+? EOL;
field_key: 'K:' key EOL;

note_length_strict: DIGIT+ '/' DIGIT+;
meter: meter_fraction;
meter_fraction: DIGIT+ '/' DIGIT+;
tempo: meter_fraction '=' DIGIT+;

key: keynote MODE_MINOR?;
keynote: BASENOTE KEY_ACCIDENTAL?;

/* Music */

body: abc_line+;
abc_line: element* EOL | mid_tune_field | COMMENT;
element: note_element | tuplet_element | BARLINE | NTH_REPEAT;

note_element: note | multi_note;

note: note_or_rest note_length?;
note_or_rest: pitch | REST;
pitch: ACCIDENTAL? BASENOTE OCTAVE?;
/*note_length: (DIGIT+)? ('/' (DIGIT+)?)?;*/
note_length: DIGIT+ ('/' DIGIT+)? | '/' DIGIT+;

/* tuplets */
tuplet_element: tuplet_spec note_element+;
tuplet_spec: '(' DIGIT;

/* chords */
multi_note: '[' note+ ']';

/* a voice field might reappear in the middle of a piece to indicate the change of a voice */
mid_tune_field: FIELD_VOICE;


/*line: (~[_] BASENOTE ('\''* | ','*) meter_fraction?)+ | [\[]:];*/

/********** Header tokens **********/
KEY_ACCIDENTAL: [#b];
MODE_MINOR: 'm';
/*TEXT: [a-zA-Z][. a-zA-Z]+;*/

/********** Music tokens **********/

/* "^" is sharp, "_" is flat, and "=" is neutral */
ACCIDENTAL: '^' | '^^' | '_' | '__' | '=';

OCTAVE: '\''+ | ','+ ;
REST: 'z';
BARLINE: '|' | '||' | '[|' | '|]' | ':|' | '|:';
NTH_REPEAT: '[1' | '[2';

/********** General tokens **********/
DIGIT: [0-9];
BASENOTE: [A-Fa-f];
EOL: '\n' | '\r''\n'?;

PLUS: '+';

/* Tell Antlr to ignore spaces around tokens. */
SPACES: [ ] -> skip;
