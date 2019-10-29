(ns price-calculator.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:body {:color "black"
          :margin-left "100px"
          :margin-right "100px"}
   [:div.price-calculator  {:position "fixed"
                            :right "20px"
                            :top "270px"}]
   [:div.choose {:margin-top "10px"}]])
