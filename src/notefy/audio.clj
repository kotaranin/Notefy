(ns notefy.audio
  (:require [clojure.java.io :as io]
            [uncomplicate.clojure-sound.core :as core]
            [uncomplicate.clojure-sound.sampled :as sound]))

(defn bytes->sample [b1 b2]
  (let [unsigned (bit-or (bit-and b1 0xFF) (bit-shift-left b2 8))
        signed (if (>= unsigned 32768) (- unsigned 65536) unsigned)]
    (/ (short signed) 32768.0)))

(defn read-samples [file-path]
  (let [file (io/file file-path)]
    (with-open [stream (sound/audio-input-stream file)]
      (let [format (sound/audio-format stream)
            total-frames (sound/frame-length stream)
            frame-size (sound/frame-size format)
            array-size (* total-frames frame-size)
            buffer (byte-array array-size)]
        (core/read! stream buffer)
        (map #(apply bytes->sample %) (partition 2 buffer))))))

(read-samples "resources/440.wav")