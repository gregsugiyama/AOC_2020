(ns day-10.puzzle
  (:require [clojure.data :as cljd]
            [santas-little-helpers :as h]))

(def test-input (->> (h/read-input "data/day_10/test.txt")
                     (map h/parse-int)
                     (sort)
                     (into [0])))

(def input (->> (h/read-input "data/day_10/input.txt")
                (map h/parse-int)
                (sort)
                (into [0])))

(def sorted-jolt-ratings (conj test-input (+ (last test-input) 3)))

(defn solution-pt1
  [input]
  (let [jolt-partition (partition 2 1 input)
        ratings        (frequencies (map (fn [[a b]]
                                           (print [a  b])
                                           (- b a)) jolt-partition))]
    (* (get ratings 1 1)
       (get ratings 3 1))))

;; Create a mapping of all positions with possible solutions set to 0
;; with the first position set to 1. Check every position for n + 1, n + 2, n + 3,
;; if they are a valid arangment in the input data, increment your path possibilites.
;; 
;; For example, [0 1 2 4]
;; At n = 1, the possible paths would be n + 1 & n + 3. This is a total of 2 diferent paths.
;; At n = 2, the possible paths would be n + 2. There is only 1 path. Still 2 different paths
;; at n = 4, there are no new possible positions. The possible paths to n = 4 are the original vector + 2 new paths = 3 total paths.

(defn solution-pt2
  [input]
  (let [first-pos  (first input)
        positions (-> (reduce (fn [acc n]
                                (assoc acc n 0)) {} input)
                      (assoc first-pos 1))
        [_ possible-arangments] (last (sort (reduce (fn [positions n]
                                                      (println (sort positions) n)
                                                      (let [n1 (get positions (- n 1) 0)
                                                            n2 (get positions (- n 2) 0)
                                                            n3 (get positions (- n 3) 0)]
                                                        (assoc positions n (+ n1 n2 n3))))
                                                    positions (rest input))))]
    possible-arangments))

(comment
;; Solution pt 1
  (solution-pt1 sorted-jolt-ratings)

;; Solution pt 2
  (solution-pt2 sorted-jolt-ratings))
