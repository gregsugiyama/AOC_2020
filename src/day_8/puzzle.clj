(ns day-8.puzzle
  (:require [santas-little-helpers :as h]))

(def input (h/read-input "data/day_8/input.txt"))

(defn parse-input
  [input-str]
  (let [[_ instruction fn-symbol delta] (flatten (re-seq #"(\w{3}) ([+-])(\d+)" input-str))]
    [instruction (resolve (symbol fn-symbol)) (h/parse-int delta)]))

(defn has-run?
  [indices i]
  (some #(= i %) indices))

(defn run-instructions
  [instructions]
  (loop [acc         0
         current-idx 0
         executed    []]
    (if (has-run? executed current-idx)
      {"acc"           acc
       "current index" current-idx
       "executed"      executed}
      (let [[instruction func delta] (get instructions current-idx)]
        (cond
          (= instruction "nop") (recur acc (inc current-idx) (conj executed current-idx))
          (= instruction "acc") (recur (func acc delta) (inc current-idx) (conj executed current-idx))
          (= instruction "jmp") (recur acc (func current-idx delta) (conj executed current-idx))
          :else (print "NO INSTRUCTION FOUND, ENDING PROGRAM "  {"acc"           acc
                                                                 "current index" current-idx
                                                                 "executed"      executed}))))))
(defn run-instructions-2
  [instructions]
  (loop [acc         0
         current-idx 0
         executed    []]
    (let [[instruction func delta] (get instructions current-idx)]
      (cond
        (has-run? executed current-idx)      :infinite-loop
        (= current-idx (count instructions)) acc
        (= instruction "nop")                (recur acc (inc current-idx) (conj executed current-idx))
        (= instruction "acc")                (recur (func acc delta) (inc current-idx) (conj executed current-idx))
        (= instruction "jmp")                (recur acc (func current-idx delta) (conj executed current-idx))
        :else                                (print "NO INSTRUCTION FOUND, ENDING PROGRAM")))))

(comment
  (def instructions (mapv parse-input input))

;; Solution pt1
  (run-instructions instructions)

;; Solution pt2
  (filter #(not= % :infinite-loop)
          (for [i     (range (count instructions))
                :when (#{"nop" "jmp"} (get-in instructions [i 0]))
                :let  [updated-instructions (update-in instructions [i 0] {"nop" "jmp"
                                                                           "jmp" "nop"})]]
            (run-instructions-2 updated-instructions))))




