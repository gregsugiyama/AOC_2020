(ns santas-little-helpers
  (:require [clojure.java.io :as io]))

(defn read-input
  "Opens a Java reader on provided input & returns a sequence
   separated by new-line"
  [input]
  (->> (io/resource input)
       (io/reader)
       (line-seq)))

(defn parse-int
  [s]
  (Integer. s))
