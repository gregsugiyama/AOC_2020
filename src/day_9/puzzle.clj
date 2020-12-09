(ns day-9.puzzle
  (:require [santas-little-helpers :as h]))

(def test-input (->> (h/read-input "data/day_9/test.txt")
                     (map h/parse-int)))

(def input (->> (h/read-input "data/day_9/input.txt")
                (map h/parse-int)))

(defn make-preambles
  [size n-col]
  (partition (+ 1 size) 1 n-col))

(defn find-preamble-solutions
  [preamble]
  (let [preamble-sum (last preamble)
        addends      (drop-last preamble)
        solutions    (for [x addends
                           y addends
                           :when (and
                                  (not= x y)
                                  (= (+ x y) preamble-sum))]
                       [x y])]
    (hash-map preamble-sum solutions)))

(comment
;; Solution pt1
  (def solution-pt1
    (->> (make-preambles 25 input)
         (map find-preamble-solutions)
         (apply merge)
         (some (fn [[k v]]
                 (when (empty? v) k)))))
;; Solution pt2
  (let [target solution-pt1]
    (loop [n-col input]
      (let [result (loop [sample-size 1]
                     (let [sample     (take sample-size n-col)
                           sample-sum (apply + sample)]
                       (cond
                         (= sample-sum target) (+ (apply min sample) (apply max sample))
                         (< sample-sum target) (recur (inc sample-size))
                         :else :no-solution)))]

        (if (#{:no-solution} result)
          (recur (next n-col))
          result)))))








