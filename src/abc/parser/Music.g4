/*
 * Compile all your grammars using
 *       java -jar ../../../lib/antlr.jar *.g4
 * then Refresh in Eclipse.
 */
grammar Music;
import Configuration;

root: header EOL* body;

header: field_number comment* field_title other_fields* field_key;

field_number: 'X:' DIGIT+ EOL;
comment: '%' TEXT EOL;
field_title: 'T:' TEXT EOL;
other_fields: field_composer | field_default_length | field_meter | field_tempo | field_voice | comment;
field_composer: 'C:' TEXT EOL;
field_default_length: 'L:' note_length_strict EOL;
field_meter: 'M:' meter EOL;
field_tempo: 'Q:' tempo EOL;
field_voice: 'V:' TEXT EOL;
field_key: 'K:' key EOL;

note_length_strict: DIGIT+ '/' DIGIT+;
meter: 'C' | 'C|' | meter_fraction;
meter_fraction: DIGIT+ '/' DIGIT+;
tempo: meter_fraction '=' DIGIT+;

key: keynote MODE_MINOR?;
keynote: BASENOTE KEY_ACCIDENTAL?;

/*whitespace: \s* EOL;*/


/* Music */

body: abc_line+;
abc_line: element* EOL | mid_tune_field | comment;
element: note_element | tuplet_element | BARLINE | NTH_REPEAT;

note_element: note | multi_note;

note: note_or_rest note_length?;
note_or_rest: pitch | REST;
pitch: ACCIDENTAL? BASENOTE OCTAVE?;
OCTAVE: '\''+ | ','+ ;
note_length: (DIGIT+)? ('/' (DIGIT+)?)?;

/* "^" is sharp, "_" is flat, and "=" is neutral */
ACCIDENTAL: '^' | '^^' | '_' | '__' | '=';

REST: 'z';

/* tuplets */
tuplet_element: tuplet_spec note_element+;
tuplet_spec: '(' DIGIT;

/* chords */
multi_note: '[' note+ ']';

BARLINE: '|' | '||' | '[|' | '|]' | ':|' | '|:';
NTH_REPEAT: '[1' | '[2';

/* a voice field might reappear in the middle of a piece to indicate the change of a voice */
mid_tune_field: field_voice;


LINE: [a-zA-Z0-9\[\]|:];
/*line: (~[_] BASENOTE ('\''* | ','*) meter_fraction?)+ | [\[]:];*/

DIGIT: [0-9];
TEXT: [a-zA-Z][. a-zA-Z]*;
KEY_ACCIDENTAL: [#b];
MODE_MINOR: 'm';
BASENOTE: [A-Fa-f];
EOL: '\n' | '\r''\n'?;

PLUS: '+';

/* Tell Antlr to ignore spaces around tokens. */
SPACES: [ ] -> skip;
