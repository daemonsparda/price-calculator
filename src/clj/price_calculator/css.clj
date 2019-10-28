(ns price-calculator.css
  (:require [garden.def :refer [defstyles]]
            [garden.selectors :refer [& nth-child]]))

(defstyles screen
  [:body {:color "black"
          :margin-left "100px"
          :margin-right "100px"}
   [:label {:display "block"}]
   #_[:div [:.price-calculator {:position "fixed"
                              :background-color "#da291c"
                              :color "#ffffff"
                              :top "0px"
                              :right "0px"
                              :padding "15px"
                              :margin "10px"
                              :border "2px solid #a40808"}]]
   [:.btn-small {:background-color "#ffffff"
                 :border "0.5px solid black"
                 :color "#da291c"}]
   [:.btn-submit {:background-color "#ffffff"
                  :border "1px solid #da291c"
                  :color "#da291c"
                  :padding "3px"
                  :margin-left "500px"
                  :display "block"}]])
