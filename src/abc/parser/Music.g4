/*
 * Compile all your grammars using
 *       java -jar ../../../lib/antlr.jar *.g4
 * then Refresh in Eclipse.
 */
grammar Music;
import Configuration;

root: header EOL* body;

header: field_number comment* field_title other_fields* field_key;

field_number: 'X:' DIGIT EOL;
comment: '%' .*? EOL;
field_title: TITLE;
other_fields: field_composer | field_default_length | field_meter | field_tempo | field_voice | comment;
field_composer: COMPOSER;
field_default_length: 'L:' note_length_strict EOL;
field_meter: ('M:C' | 'M:C|' | 'M:' meter) EOL;
field_tempo: 'Q:' tempo EOL;
field_voice: 'V:' ~EOL+? EOL;
field_key: key EOL;

note_length_strict: DIGIT '/' DIGIT;
meter: meter_fraction;
meter_fraction: DIGIT '/' DIGIT;
tempo: meter_fraction EQUALS DIGIT;

key: KEYNOTE MODE_MINOR?;
//keynote: BASENOTE KEY_ACCIDENTAL?;
KEYNOTE: 'K:' (BASENOTE | BASENOTE '#' | 'Ab' | 'Bb' | 'Cb' | 'Db' | 'Eb' | 'Fb' | 'Gb');

/* Music */

body: abc_line+;
abc_line: element* EOL | mid_tune_field | comment;
element: note_element | tuplet_element | BARLINE | REPEAT | NTH_REPEAT;

note_element: note | multi_note;

note: note_or_rest note_length?;
note_or_rest: pitch | REST;
pitch: accidental? BASENOTE OCTAVE?;
/*note_length: (DIGIT)? ('/' (DIGIT)?)?;*/
note_length: DIGIT | DIGIT? '/' DIGIT?;

/* tuplets */
tuplet_element: tuplet_spec note_element+;
tuplet_spec: '(' DIGIT;

/* chords */
multi_note: '[' note+ ']';

/* a voice field might reappear in the middle of a piece to indicate the change of a voice */
mid_tune_field: field_voice;

/********** Header tokens **********/
//KEY_ACCIDENTAL: [#b];
MODE_MINOR: 'm';
TITLE: 'T:' .*? EOL;
COMPOSER: 'C:' ~[|]*? EOL;
/*TEXT: [a-zA-Z][. a-zA-Z]+;*/

/********** Music tokens **********/

/* "^" is sharp, "_" is flat, and "=" is neutral */
accidental: '^' | '^^' | '_' | '__' | EQUALS;

OCTAVE: '\''+ | ','+ ;
REST: 'z';
REPEAT: ':|' | '|:' | '[|' | '|]';
BARLINE: '|' | '||';
NTH_REPEAT: '[1' | '[2';

/********** General tokens **********/
DIGIT: [0-9]+;
BASENOTE: [A-Ga-g];
EQUALS: '=';
EOL: '\n' | '\r''\n'?;

PLUS: '+';

/* Tell Antlr to ignore spaces around tokens. */
SPACES: [ ] -> skip;
