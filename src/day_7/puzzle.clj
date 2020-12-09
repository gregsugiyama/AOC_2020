(ns day-7.puzzle
  (:require [clojure.string :as str]
            [santas-little-helpers :as h]))

(def input (h/read-input "data/day_7/test_2.txt"))

(defn parse-input
  [input]
  (let [[[_ _ bag] & elements] (re-seq #"(?:^|(\d+) )(\w+ \w+) bags?" input)
        sub-bags               (map (fn [[_ quantity type]]
                                      [(h/parse-int quantity) type]) elements)]
    (hash-map bag sub-bags)))

(def bag-schema (->> (map parse-input input)
                     (apply merge)))

(defn contains-bag?
  [outer-bag bag-type]
  (->> (get bag-schema outer-bag)
       (some (fn [[_ bt]]
               (cond
                 (= bt bag-type) true
                 :else           (contains-bag? bt bag-type))))))

(defn nested-bag-count
  [bag-type]
  (->> (get bag-schema bag-type)
       (reduce (fn [acc [n bt]]
                 (+ n acc
                    (* n (nested-bag-count bt)))) 0)))

(comment
;; Solution pt1
  (->> bag-schema
       (filter (fn [[outer-bag _]]
                 (contains-bag? outer-bag "shiny gold")))
       (count))

;; Solution pt2
  (nested-bag-count "shiny gold"))





