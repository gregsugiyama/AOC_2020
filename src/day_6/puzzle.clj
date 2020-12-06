(ns day-6.puzzle
  (:require [clojure.string        :as str]
            [santas-little-helpers :as h]))

(def input (->> (h/read-input "data/day_6/input.txt")
                (partition-by #(= "" %))
                (filter #(not (contains? (set %) "")))))

(defn count-individual-answers
  [coll]
  (->> (map #(str/split % #"") coll)
       (flatten)
       (distinct)
       (count)))

(defn count-group-answers
  [coll]
  (let [parsed-coll (map #(str/split % #"") coll)
        answer-map  (hash-map :group-size (count parsed-coll)
                              :answers    (frequencies (flatten parsed-coll)))]
    (-> (filter (fn [[_ answer-count]]
                  (= answer-count (:group-size answer-map))) (:answers answer-map))
        (count))))


(defn solution
  [input count-fn]
  (let [total-positive-answers (->> (map count-fn input)
                                    (apply +))]
    total-positive-answers))

(comment
  ;; Part 1
  (solution input count-individual-answers)

  ;;Part 2
  (solution input count-group-answers))


