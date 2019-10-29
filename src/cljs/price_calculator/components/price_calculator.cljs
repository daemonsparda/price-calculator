(ns price-calculator.components.price-calculator
  (:require [re-frame.core :as rf]
            [reagent.core :as reagent]
            [camel-snake-kebab.core :as csk]
            [price-calculator.events :as events]
            [price-calculator.subs :as subs]
            [price-calculator.components.choose :as choose]
            [price-calculator.utils.calculator :as calc]
            [goog.string :as g-str]
            [clojure.string :as str]
            [cljs.reader :refer [read-string]]))

(defn add-selection
  []
  (let [selection @(rf/subscribe [::subs/current-selection])]
    [:div.text-right
     [:input {:type "button"
              :value "Add Selection"
              :class "btn btn-outline-danger"
              :on-click #(if (= "Additional Features" (:product-group selection))
                           (rf/dispatch [::events/add-features selection])
                           (rf/dispatch [::events/add-selection selection]))}]]))

(defn selection-table-row
  [selection]
  (let []
    [:tr
     [:td (:product-group selection)]
     [:td (:instance-type selection)]
     [:td (or (:gpu-type selection) "N/A")]
     [:td (:local-storage-size selection)]
     [:td (or (:snapshot-amount selection) "N/A")]
     [:td (:windows-license? selection)]]))

(defn selection-table-body
  [selection-list]
  [:tbody
   (for [selection selection-list]
     ^{:key (str "selection-" (rand 300))} ;; Eww, eventually we'll get the same number here D:
     [selection-table-row selection])])

(defn selection-table
  []
  (let [selection-list @(rf/subscribe [::subs/selection-list])]
    (when (seq selection-list)
      [:div
       [:h5 "List of products chosen"]
       [:table {:class "table red-table"}
        [:thead
         [:tr
          [:th "Product Group"]
          [:th "Instance type"]
          [:th "GPU-type"]
          [:th "Local storage size"]
          [:th "Snapshots"]
          [:th "Windows-license"]]]
        [selection-table-body (filter #(not= "Additional Features" (:product-group %)) selection-list)]]])))

(defn additional-features-table-row
  [selection]
  [:tr
   [:td (:dns-package selection)]
   [:td (or (:eip-address-amount selection) "N/A")]
   [:td (or (:custom-template-zones selection) "N/A")]
   [:td (or (:custom-template-size selection) "N/A")]
   [:td (or (:object-storage-size selection) "N/A")]])

(defn additional-features-table-body
  [selection]
  [:tbody
   [additional-features-table-row selection]])

(defn additional-features-table
  []
  (let [selection-list @(rf/subscribe [::subs/selection-list])]
    (when @(rf/subscribe [::subs/additional-features])
      [:div
       [:h5 "Additional features chosen"]
       [:table {:class "table red-table"}
        [:thead
         [:tr
          [:th "DNS Package"]
          [:th "Elastic IP Address"]
          [:th "Custom Template Zones"]
          [:th "Custom Template Size"]
          [:th "Object Storage"]]]
        [additional-features-table-body @(rf/subscribe [::subs/additional-features])]]])))

(defn price-calculator []
  (let [product-group-key @(rf/subscribe [::subs/product-group-key])
        selection-list @(rf/subscribe [::subs/selection-list])
        additional-features @(rf/subscribe [::subs/additional-features])
        selection-list (conj selection-list additional-features)
        currency-type @(rf/subscribe [::subs/currency-type])
        license-win-price @(rf/subscribe [::subs/license-win])
        compute-pricing @(rf/subscribe [::subs/compute-pricing])
        object-storage-pricing @(rf/subscribe [::subs/object-storage-pricing])
        dns-package-price (rf/subscribe [::subs/dns-package-price])]
    [:div.price-calculator
     [:div {:class "price-calculator"}
      [choose/currency-type]
      (when (or (seq selection-list) (seq additional-features))
        [:div
         [:h5 (str "Total price: "
                   (calc/calculate-total
                    selection-list
                    currency-type
                    compute-pricing
                    license-win-price
                    object-storage-pricing)
                   "/hour")]
         [:h5 @dns-package-price]])]]))

