(ns day-1.puzzle-test
    (:require
     [day-1.puzzle :as sut]
     [clojure.test :as t :refer [deftest testing is]]))

(def test-input '(1721 979 366 299 675 1456))

(deftest solution-pt1
  (let [expected 514579
        actual   (sut/solution-pt1 test-input)]
   (testing "When given a list of numbers, two numbers who's sum equals '2020' should multiply to equal 514579"
     (is (= expected actual)))))

(deftest solution-pt2
  (let [expected 241861950
        actual   (sut/solution-pt2 test-input)]
   (testing "When given a list of numbers, three numbers who's sum equals '2020' should multiply to equal 241861950"
     (is (= expected actual)))))

