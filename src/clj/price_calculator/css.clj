(ns price-calculator.css
  (:require [garden.def :refer [defstyles]]
            [garden.selectors :refer [& nth-child]]))

(defstyles screen
  [:body {:color "black"
          :margin-left "100px"
          :margin-right "100px"}
   [:label {:display "block"}]
   [:div [:.price-calculator {:position "fixed"
                              :background-color "#da291c"
                              :color "#ffffff"
                              :top "0px"
                              :right "0px"
                              :padding "15px"
                              :margin "10px"
                              :border "2px solid #a40808"}]]
   [:.btn {:background-color "#da291c"
           :border "1px solid white"
           :color "#ffffff"
           :box-shadow "5px 5px 2.5px grey"
           :padding "5px"
           :margin "5px"
           :display "block"
           :margin-left "25px"}]
   [:.btn-small {:background-color "#ffffff"
                 :border "0.5px solid black"
                 :color "#da291c"}]
   [:.btn-submit {:background-color "#ffffff"
                  :border "1px solid #da291c"
                  :color "#da291c"
                  :padding "3px"
                  :margin-left "500px"
                  :display "block"}]
   [:table.red-table {:position "fixed"
                      :width "auto"
                      :border "2px solid #a40808"
                      :background-color "#da291c"
                      :color "white"
                      :text-align "center"
                      :right "0px"
                      :bottom "0px"
                      :display "block"
                      :border-collapse "collapse"}
    [:th :td {:border "1px solid #aaaaaa"
              :padding "3px 2px"}]
    [:tbody [:td {:font-size "13px"
                  :background-color "white"
                  :color "black"}]]]])



;; selection color rgb(77, 0, 5)

;; table.redTable tr:nth-child(even) {
;;   background: #F5C8BF;
;; }
;; table.redTable thead {
;;   background: #A40808;
;; }
;; table.redTable thead th {
;;   font-size: 19px;
;;   font-weight: bold;
;;   color: #FFFFFF;
;;   text-align: center;
;;   border-left: 2px solid #A40808;
;; }
;; table.redTable thead th:first-child {
;;   border-left: none;
;; }

;; table.redTable tfoot {
;;   font-size: 13px;
;;   font-weight: bold;
;;   color: #FFFFFF;
;;   background: #A40808;
;; }
;; table.redTable tfoot td {
;;   font-size: 13px;
;; }
;; table.redTable tfoot .links {
;;   text-align: right;
;; }
;; table.redTable tfoot .links a{
;;   display: inline-block;
;;   background: #FFFFFF;
;;   color: #A40808;
;;   padding: 2px 8px;
;;   border-radius: 5px;
;; }
