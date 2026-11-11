(ns notefy.pitch)

(defn log2 [x]
  (/ (Math/log x) (Math/log 2)))

(defn round [x d]
  (let [f (Math/pow 10 d)]
    (/ (Math/round (* x f)) f)))

(defn freq->midi [freq]
  (Math/round (+ 69 (* 12 (log2 (/ freq 440))))))

(defn midi->freq [midi]
  (round (* 440 (Math/pow 2 (/ (- midi 69) 12))) 2))

(defn build-note-name [midi notes]
  (str (notes (mod midi 12)) (dec (quot midi 12))))

(defn midi->note [midi acc]
  (let [sharp-notes ["C" "C#" "D" "D#" "E" "F" "F#" "G" "G#" "A" "A#" "B"]
        flat-notes ["C" "Db" "D" "Eb" "E" "F" "Gb" "G" "Ab" "A" "Bb" "B"]]
    (cond
      (= acc :sharp) (build-note-name midi sharp-notes)
      (= acc :flat) (build-note-name midi flat-notes)
      :else (throw (IllegalArgumentException. (str "Unsupported accidental!"))))))

(defn freq->note [freq acc]
  (midi->note (freq->midi freq) acc))

(defn ideal-freq [f]
  (midi->freq (freq->midi f)))

(defn cents-off [f]
  (round (* 1200 (log2 (/ f (ideal-freq f)))) 2))

(defn transcribe-sequence [fs acc]
  (map (fn [f]
         {:note (freq->note f acc) :cents (cents-off f)})
       fs))