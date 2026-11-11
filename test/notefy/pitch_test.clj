(ns notefy.pitch-test
  (:require [midje.sweet :refer :all]
            [notefy.pitch :as pitch]))

(fact "440 Hz is MIDI note 69."
      (pitch/freq->midi 440.0) => 69)

(fact "261.63 Hz is MIDI note 60."
      (pitch/freq->midi 261.63) => 60)

(fact "MIDI note 69 is 440 Hz."
      (pitch/midi->freq 69) => 440.0)

(fact "MIDI note 60 is 261.63 Hz."
      (pitch/midi->freq 60) => 261.63)

(fact "MIDI note 68 is a G#4."
      (pitch/midi->note 68 :sharp) => "G#4")

(fact "MIDI note 68 is a Ab4."
      (pitch/midi->note 68 :flat) => "Ab4")

(fact "Only sharps and flats are supported."
      (pitch/midi->note 0 :whatever) => (throws IllegalArgumentException))

(fact "4978.03 Hz is a D#8."
      (pitch/freq->note 4978.03 :sharp) => "D#8")

(fact "4978.03 Hz is a Eb8."
      (pitch/freq->note 4978.03 :flat) => "Eb8")

(fact "Ideal frequency of 445 Hz is 440 Hz."
      (pitch/ideal-freq 445) => 440.0)

(fact "445 Hz is X cents sharp of 440 Hz."
      (pitch/cents-off 445) => 19.56)

(fact "Frequency sequence [440.0 445.0 261.63] maps to [{:note \"A4\" :cents 0.0} {:note \"A4\" :cents 19.56} {:note \"C4\" :cents 0.0}]"
      (pitch/transcribe-sequence [440.0 445.0 261.63] :sharp) => [{:note "A4", :cents 0.0} {:note "A4", :cents 19.56} {:note "C4", :cents 0.0}])