(ns day-2.puzzle
  (:require [clojure.string  :as str]
            [clojure.java.io :as io]
            [helpers         :as h]))

(def puzzle-input (->> (io/resource "data/day_2/input.txt")
                       (io/reader)
                       (line-seq)))

(defn parse-input
  [s]
  (let [[_ min max char password] (re-find #"(\d+)-(\d+) (.): (.*)" s)]
    [(h/str->int min) (h/str->int max) char password]))

(def parsed-input (map parse-input puzzle-input))

(defn valid-password-pt1?
  [[min max char password]]
  (let [password-chars     (str/split password #"")
        char-counts        (frequencies password-chars)
        frequency          (get char-counts char 0)
        is-valid-password? (<= min frequency max)]
    is-valid-password?))

(defn solution-pt1
  [input]
  (let [valid-passwords (->> (map valid-password-pt1? input)
                             (filter true?))]
    (count valid-passwords)))

(defn valid-password-pt2?
  [[pos-1 pos-2 char password]]
  (let [password-chars     (str/split password #"")
        first-char         (get password-chars (- pos-1 1) "")
        second-char        (get password-chars (- pos-2 1) "")
        is-valid-password? (= 1 (count (filter true? [(= char first-char)
                                                      (= char second-char)])))]
    is-valid-password?))

(defn solution-pt2
  [input]
  (let [valid-passwords (->> (map valid-password-pt2? input)
                             (filter true?))]
    (count valid-passwords)))

(comment
  (solution-pt1 parsed-input)
  (solution-pt2 parsed-input))
