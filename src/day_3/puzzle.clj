(ns day-3.puzzle
  (:require [clojure.java.io :as io]
            [clojure.string  :as str]))

(def test-input (->> (io/resource "data/day_3/test.txt")
                     (io/reader)
                     (line-seq)
                     (mapv #(str/split % #""))))

(def puzzle-input (->> (io/resource "data/day_3/input.txt")
                       (io/reader)
                       (line-seq)
                       (mapv #(str/split % #""))))

(defn is-tree?
  [tree-vec x y]
  (let [width (-> (first tree-vec)
                  (count))
        pos   (mod x width)]
    (case (get-in tree-vec [y pos])
      "#" true
      "." false
      "END")))

(defn solution-pt1
  [input]
  (loop [x           0
         y           0
         trees       0]
    (let [new-x    (+ x 3)
          new-y    (+ y 1)
          tree?    (is-tree? input new-x new-y)]
      (cond
        (= tree? "END") (println "END OF THE RUN" trees)
        (= tree? true)  (recur new-x new-y (inc trees))
        (= tree? false)  (recur new-x new-y trees)))))

(comment
  (is-tree? test-input 4 11) ;; -> END
  (is-tree? test-input 5 1) ;; -> false
  (is-tree? test-input 4 1) ;; -> true
  (solution-pt1 puzzle-input))




