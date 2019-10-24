(ns price-calculator.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:body {:color "black"}
   [:label {:display "block"}]
   [:div [:.price-calculator {:position "fixed"
                              :background-color "#da291c"
                              :color "#ffffff"
                              :padding "15px"
                              :padding-top "0px"
                              :padding-bottom "0px"
                              :right "500px";; Remember to change this 
                              }]]
   [:.btn {:background-color "#da291c"
           :border "1px solid white"
           :color "#ffffff"
           :padding "3px"
           :display "block"
           :margin-left "50px"}]
   [:.btn-submit {:background-color "#ffffff"
                  :border "1px solid #da291c"
                  :color "#da291c"
                  :padding "3px"
                  :margin-left "500px"
                  :display "block"}]
   [:input.invalid {:border "1px solid red"}]
   ])
