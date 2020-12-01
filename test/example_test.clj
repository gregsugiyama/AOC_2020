(ns example-test
  (:require
   [example :refer [example-fn]] 
   [clojure.test :as t :refer [deftest testing is]]))

(deftest example-test
  (let [expected 2
        actual (example-fn 1 2)]
    (testing "FIXME this test should fail"
     (is (= expected actual)))))
