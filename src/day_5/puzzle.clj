(ns day-5.puzzle
  (:require [santas-little-helpers :as h]
            [clojure.string        :as str]))

(def input (h/read-input "data/day_5/input.txt"))

(defn decode
  [string]
  (let [binary-map {\B 1 \F 0 \R 1 \L 0}]
    (-> (str/escape string binary-map)
        (h/parse-int 2))))

(defn find-seat
  [boarding-pass]
  (let [[row seat]  (partition-all 7 boarding-pass)
        row-number  (decode (apply str row))
        seat-number (decode (apply str seat))]
    (+ (* 8 row-number) seat-number)))

(comment
  ;; Solution Pt1
  (apply max (map find-seat input))

  ;; Solution Pt2
  (def taken-seats (sort (map find-seat input)))

  (:my-id (reduce (fn [acc seat-id]
                    (cond
                      (nil? (get acc :prev-id nil))      (assoc acc :prev-id seat-id)
                      (< 1 (* 1 (- seat-id (get acc :prev-id nil)))) (assoc acc :prev-id seat-id
                                                                            :my-id (- seat-id 1))
                      :else (assoc acc :prev-id seat-id))) {} taken-seats)))



