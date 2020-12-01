(ns day-1.puzzle
  (:require [clojure.java.io :as io]
            [helpers         :as h]))

(def puzzle-input (->> (io/reader "resources/data/day_1/input.txt")
                       (line-seq)
                       (map h/str->int)))

(defn solution-pt1
  ([input]
   (some int (distinct (for [x input
                             y input
                             :when (= (+ x y) 2020)]
                         (* x y))))))

(defn solution-pt2
  ([input]
   (some int (distinct (for [x input
                             y input
                             z input
                             :when (= (+ x y z) 2020)]
                         (* x y z))))))

(comment 
  (solution-pt1 puzzle-input)
  (solution-pt2 puzzle-input))
