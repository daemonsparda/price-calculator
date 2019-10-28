(ns price-calculator.views
  (:require
   [re-frame.core :as rf]
   [price-calculator.subs :as subs]
   [price-calculator.events :as events]
   [price-calculator.components.choose :as choose]
   [price-calculator.components.price-calculator :as pc]))


;; home

(defn home-panel []
  (let [name (rf/subscribe [::subs/name])]
    [:div
     [:h1 (str @name " Assessment")]
     [:a {:href "#/lets-get-started"} "Let's get started"]]))

(defn additional-features [] 
  [:div
   [choose/dns-package]
   [:p]
   [choose/eip-address]
   [:p]
   [choose/custom-templates]
   [:p]
   [choose/object-storage]])

(defn price-calculator []
  [:div.container
   [:div {:class "px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center"}
    [:div {:class "display-4"}
     "Price Calculator Assessment"
     [:p {:class "lead"}
      "You can find more information about Exoscale products at "
      [:a {:href "https://www.exoscale.com/pricing/"}
       "https://www.exoscale.com/pricing/"]]]]
   [choose/product-group]
   [:p]
   (if (= :additional-features @(rf/subscribe [::subs/product-group-key]))
     [additional-features]
     [:div
      [:p]
      [choose/gpu-type @(rf/subscribe [::subs/product-group-key])]
      [:p]
      [choose/instance-type @(rf/subscribe [::subs/product-group-key])]
      [:p]
      [choose/license]
      [:p]
      [choose/local-storage-size]
      [:p]
      [choose/snapshot]
      ])
   [:p]
   [pc/add-selection]
   [:p]
   [pc/price-calculator]
   [pc/selection-table]
   [pc/additional-features-selection-table]])

;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :price-calculator [price-calculator]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

;; Use this for layout
(defn main-panel []
  (let [active-panel (rf/subscribe [::subs/active-panel])]
    [:div.container
     [show-panel @active-panel]]))
