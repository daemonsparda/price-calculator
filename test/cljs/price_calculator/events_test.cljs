(ns price-calculator.events-test
  (:require [price-calculator.events :as sut]
            [cljs.test :refer-macros [deftest is testing run-tests]]))

(def test-db {:product-group ""})

(deftest choose-a-product-test
  (testing "Ensure choose a product properly updates db."
    (is (= (sut/set-product-group test-db [nil "Hello"]) {:product-group "Hello"}))))
