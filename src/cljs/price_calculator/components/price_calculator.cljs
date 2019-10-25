(ns price-calculator.components.price-calculator
  (:require [re-frame.core :as rf]
            [reagent.core :as reagent]
            [camel-snake-kebab.core :as csk]
            [price-calculator.events :as events]
            [price-calculator.subs :as subs]
            [price-calculator.components.choose :as choose]
            [goog.string :as g-str]
            [clojure.string :as str]
            [cljs.reader :refer [read-string]]))

(defn add-selection
  []
  (let [selection {:product-group @(rf/subscribe [::subs/product-group])
                   :instance-type @(rf/subscribe [::subs/instance-type])
                   :gpu-type @(rf/subscribe [::subs/gpu-type])
                   :local-storage-size @(rf/subscribe [::subs/local-storage-size])
                   :windows-license? @(rf/subscribe [::subs/windows-license?])}]
    [:input {:type "button"
             :value "Add Selection"
             :class "btn-submit"
             :on-click #(rf/dispatch [::events/add-selection selection])}]))

(defn json-visualizer [licenses open-compute sos]
  [:div
   (str licenses)
   [:table
    [:thead
     [:th "key"]
     [:th "amount"]]
    (map (fn [x] 
           [:tr
            [:td (first x)]
            [:td (last x)]]) (seq open-compute))]
   [:table
    [:thead
     [:th "key"]
     [:th "amount"]]
    (map (fn [x] 
           [:tr
            [:td (first x)]
            [:td (last x)]]) (seq sos))]])

(defn generate-compute-key
  [selection]
  (when (seq selection)
    (csk/->snake_case_keyword
     (str "running_" (case (:product-group selection)
                       "Standard Instances" ""
                       "Storage Optimized Instances" "storage_"
                       "GPU Instances" (if (= (:gpu-type selection) :gpu)
                                         "gpu2_"
                                         "gpu_")
                       :default "")
          (str/lower-case (:instance-type selection))))))

(defn calculate-compute-cost
  [instance-price license-win-price local-storage-price local-storage-size]
  (let [instance-price (read-string instance-price)
        license-win-price (read-string license-win-price)
        local-storage-price (read-string local-storage-price)]
    (+ instance-price
       license-win-price
       (* local-storage-price local-storage-size))))

(defn calculate-product-cost
  [selection currency-type compute license sos]
  (let [CHF->EUR 0.91
        CHF->USD 1.01
        currency-fn (case currency-type
                      "CHF" (fn [x] x)
                      "EUR" (fn [x] (* x CHF->EUR))
                      "USD" (fn [x] (* x CHF->USD)))
        license (if (= "Yes" (:windows-license? selection))
                  license
                  "0")
        local-storage-key (if (= "Standard Instances" (:product-group selection))
                            :volume
                            :volume_data)
        compute-key (generate-compute-key selection)
        compute-price (get compute compute-key)]
    (+ (read-string compute-price)
       (read-string license)
       (* (read-string (local-storage-key compute)) (:local-storage-size selection)))))


(defn calculate-total
  [selection-list currency-type compute-pricing license-win-price object-storage-pricing]
  (let [currency-symbol (case currency-type
                       "CHF" (g-str/unescapeEntities "&#8355;")
                       "EUR" (g-str/unescapeEntities "&#8364;")
                       "USD" "$")]
    (if (= [] selection-list)
      (str currency-symbol "0.00")
      (str currency-symbol
           (apply
            +
            (for [selection selection-list]
              (calculate-product-cost selection currency-type compute-pricing license-win-price object-storage-pricing)))))))

(defn selection-row
  [selection]
  [:tr
   [:td (:product-group selection)]
   [:td (:instance-type selection)]
   [:td (:local-storage-size selection)]
   [:td (:windows-license? selection)]
   [:td (or (:gpu-type selection) "N/A")]
   [:td [:input {:type "button" :class "btn-small" :value "x"}]]])

(defn selections
  [selection-list]
  [:tbody
   (for [selection selection-list]
     ^{:key (str "selection-" (rand 300))} ;; Eww, eventually we'll get the same number here D:
     [selection-row selection])])

(defn selection-table
  []
  (let [selection-list @(rf/subscribe [::subs/selection-list])]
    [:table {:class "red-table"}
     [:thead
      [:tr
       [:th "Product Group"]
       [:th "Instance type"]
       [:th "Local storage size"]
       [:th "Windows-license"]
       [:th "GPU-type"]
       [:th "Remove?"]]]
     [selections selection-list]]))

(defn price-calculator []
  (let [product-group-key @(rf/subscribe [::subs/product-group-key])
        selection-list @(rf/subscribe [::subs/selection-list])
        currency-type @(rf/subscribe [::subs/currency-type])
        license-win-price (rf/subscribe [::subs/license-win])
        compute-pricing (rf/subscribe [::subs/compute-pricing])
        object-storage-pricing (rf/subscribe [::subs/object-storage-pricing])]
    [:div
     [:div
      [choose/product-group]
      (if (= product-group-key :gpu-instances)
        [:div
         [choose/gpu-type]
         [choose/gpu-instance-type]]
        [choose/instance-type product-group-key])
      [choose/local-storage-size]
      [choose/license]
      [add-selection]]
     [:div {:class "price-calculator"}
      [choose/currency-type]
      [:p (str "Total price: "
               (calculate-total
                selection-list
                currency-type
                @compute-pricing
                @license-win-price
                @object-storage-pricing)
               "/hour")]]
     [selection-table]]))

