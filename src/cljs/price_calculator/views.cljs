(ns price-calculator.views
  (:require
   [re-frame.core :as rf]
   [price-calculator.subs :as subs]
   [price-calculator.events :as events]
   [price-calculator.components.choose :as choose]
   [price-calculator.components.price-calculator :as pc]))


;; home

(defn home-panel
  []
  (let [name (rf/subscribe [::subs/name])]
    [:div
     [:h1 (str @name " Assessment")]
     [:a {:href "#/price-calculator"} "Price Calculator"]]))

(defn additional-features
  []
  [:div
   [choose/eip-address]
   [choose/custom-templates]
   [choose/object-storage]
   [choose/dns-package]])

(defn price-calculator-header
  []
  [:div {:class "px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center"}
    [:div {:class "display-4"}
     "Price Calculator Assessment"
     [:p {:class "lead"}
      "You can find more information about Exoscale products at "
      [:a {:href "https://www.exoscale.com/pricing/"}
       "https://www.exoscale.com/pricing/"]]]])

(defn price-calculator []
  [:div.container
   [price-calculator-header]
   [choose/product-group]
   (if (= :additional-features @(rf/subscribe [::subs/product-group-key]))
     [additional-features]
     [:div
      [choose/gpu-type]
      [choose/instance-type]
      [choose/license]
      [choose/local-storage-size]
      [choose/snapshot]])
   [pc/add-selection]
   [pc/price-calculator]
   [pc/selection-table]
   [pc/additional-features-table]])

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
