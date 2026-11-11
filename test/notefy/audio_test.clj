(ns notefy.audio-test
  (:require [midje.sweet :refer :all]
            [notefy.audio :as audio]))

(fact "Maps byte values 128 and 127 to about 0.996."
      (audio/bytes->sample 128 127) => (roughly 0.996))

(fact "Returns non-empty sequence of sample values from a .wav file."
      (count (audio/read-samples "resources/440.wav")) => (roughly 705600))

(fact "All sample values are between -1.0 and 1.0."
      (every? #(<= -1.0 % 1.0) (audio/read-samples "resources/440.wav")) => true)