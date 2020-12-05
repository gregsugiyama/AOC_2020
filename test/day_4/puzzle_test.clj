(ns day-4.puzzle-test
  (:require
   [day-4.puzzle          :as sut]
   [clojure.test          :as t :refer [deftest testing is]]))

(def test-passports [{:ecl "gry", :pid "860033327", :eyr "2020", :hcl "#fffffd", :byr "1937", :iyr "2017", :cid "147", :hgt "183cm"}
                     {:iyr "2013", :ecl "amb", :cid "350", :eyr "2023", :pid "028048884", :hcl "#cfa07d", :byr "1929"}
                     {:hcl "#ae17e1", :iyr "2013", :eyr "2024", :ecl "brn", :pid "760753108", :byr "1931", :hgt "179cm"}
                     {:hcl "#cfa07d", :eyr "2025", :pid "166559648", :iyr "2011", :ecl "brn", :hgt "59in"}])

(deftest puzzle-4-test
  (testing "A list of passport strings should format to a vector of maps"
    (let [expected test-passports
          actual   (sut/parse-input "resources/data/day_4/test.txt")]
      (is (= expected actual)))))

(deftest solution-test
  (let [expected 2
        actual   (sut/solution "resources/data/day_4/test.txt")]
    (is (= expected actual))))
