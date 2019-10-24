(ns price-calculator.views
  (:require
   [re-frame.core :as rf]
   [price-calculator.subs :as subs]
   [price-calculator.events :as events]
   [price-calculator.components.price-calculator :as pc]))


;; home

(defn home-panel []
  (let [name (rf/subscribe [::subs/name])]
    [:div
     [:h1 (str @name "Assessment")]
     [:a {:href "#/lets-get-started"} "Let's get started"]]))


;; about

;; should only have the price calculator at the end of the project.
(defn lets-get-started [] 
  [:div
   [:h1 "Let's get started"]
   [pc/price-calculator]
   [:div
    [:a {:href "#/"}
     "Return to Home Page"]]])


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :lets-get-started [lets-get-started]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (rf/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
