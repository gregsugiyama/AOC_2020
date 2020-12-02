(ns day-2.puzzle-test
  (:require
   [day-2.puzzle :as sut]
   [clojure.test :as t :refer [deftest testing is]]))

(def test-input '([1 3 "a" "abcde"]
                  [1 3 "b" "cdefg"]
                  [2 9 "c" "ccccccccc"]))

(deftest solution-pt1
  (testing "When given a password vector, (last vector) should occur at least (first vector) times and <= (second vector)"
    (is (= true (sut/valid-password-pt1? [1 3 "a" "abcde"])) "[1 3 'a' 'abcde'] should be true")
    (is (= false (sut/valid-password-pt1? [2 3 "a" "abcde"])) "[2 3 'a' 'abcde'] should be false"))
  (testing "Should return true when given a map of password vectors & passwords"
    (let [expected 2
          actual   (sut/solution-pt1 test-input)]
      (is (= expected actual)))))

(deftest solution-pt2
  (testing "Password chars should only occur at exactly one of two given positions"
    (is (= true (sut/valid-password-pt2? [1 3 "a" "abcde"])) "[1 3 'a' 'abcde'] should be true")
    (is (= false (sut/valid-password-pt2? [2 3 "a" "abcde"])) "[1 3 'a' 'abade'] should be false"))
  (testing "Should return true when given a map of password vectors & passwords"
    (let [expected 1
          actual   (sut/solution-pt2 test-input)]
      (is (= expected actual)))))

