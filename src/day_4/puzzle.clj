(ns day-4.puzzle
  (:require [clojure.spec.alpha    :as s]
            [clojure.string        :as str]
            [santas-little-helpers :as h]))

(defn is-valid-height?
  [s]
  (let [[_ num unit] (re-find #"(\d+)(in|cm)" s)]
    (case unit
      "cm" (<= 150 (h/parse-int num) 193)
      "in" (<= 59 (h/parse-int num) 76)
      false)))

;; Turn on & evaluate these spec's for part 2

;; (s/def ::byr #(<= 1920 (h/parse-int %) 2002))
;; (s/def ::iyr #(<= 2010 (h/parse-int %) 2020))
;; (s/def ::eyr #(<= 2020 (h/parse-int %) 2030))
;; (s/def ::hgt is-valid-height?)
;; (s/def ::hcl #(re-matches #"^#(?:[0-9a-fA-F]{6}){1,2}$" %))
;; (s/def ::ecl #(#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} %))
;; (s/def ::pid #(re-matches #"^\d{9}$" %))

(s/def ::passport (s/keys :req-un [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]
                          :opt-un [::cid]))

(defn make-passport-map
  [kv-string]
  (let [kv-list      (re-seq #"\w+:.*?(?=\s+\w+:|$)" kv-string)
        passport-map (->> (map (fn [s]
                                 (let [[_ k v] (re-matches #"(\w{3}):(.*)" s)]
                                   {(keyword k) (str/trim v)})) kv-list)
                          (apply merge))]
    passport-map))

(defn parse-input
  [input-path]
  (let [by-passport (-> (slurp input-path)
                        (str/split #"\R\R"))
        trimmed     (mapv #(str/replace % "\n" " ") by-passport)
        passport-v  (mapv make-passport-map trimmed)]
    passport-v))

(defn solution
  [input]
  (let [parsed-input         (parse-input input)
        valid-passport?      #(s/valid? ::passport %)
        valid-passport-count (count (keep #{true} (map valid-passport? parsed-input)))]
    valid-passport-count))

(comment
  (solution "resources/data/day_4/test.txt"))


