(ns price-calculator.components.price-calculator
  (:require [re-frame.core :as rf]
            [reagent.core :as reagent]
            [camel-snake-kebab.core :as csk]
            [price-calculator.events :as events]
            [price-calculator.subs :as subs]
            [goog.string :as g-str]
            [clojure.string :as str]
            [decimal.core :as dc]))

(defn choose-product-group [product-group-list]
  (let [product-group-list @(rf/subscribe [::subs/product-group-list])]
    [:div
     [:label {:for "product-group"} "First let's choose a product type: "
      [:strong (str @(rf/subscribe [::subs/product-group]))]]
     (for [product-group product-group-list]
       ^{:key (str "product-group-" product-group)}
       [:input {:type "button"
                :value product-group
                :class "btn"
                :on-click #(do (rf/dispatch [::events/set-product-group-key (-> % .-target .-value)])
                               (rf/dispatch [::events/set-product-group (-> % .-target .-value)]))}])]))

(defn choose-instance-type
  [product-group-key]
  (let [instance-type-list @(rf/subscribe [::subs/instance-type-list product-group-key])]
    [:div
     [:label {:for "instance-type"} "Choose an instance type: "
      [:strong (str @(rf/subscribe [::subs/instance-type]))]]
     (for [instance-type instance-type-list]
       ^{:key (str "instance-type-" instance-type)}
       [:input {:type "button"
                :value instance-type
                :class "btn"
                :on-click #(do (rf/dispatch [::events/set-instance-type (-> % .-target .-value)])
                               (rf/dispatch [::events/set-local-storage-min product-group-key instance-type])
                               (rf/dispatch [::events/set-local-storage-max product-group-key instance-type]))}])]))

(defn choose-gpu-type
  []
  [:div "Choose the type of GPU:" [:strong @(rf/subscribe [::subs/gpu-type])]
   [:input {:type "button"
            :class "btn"
            :value "GPU2 (Tesla V100)"
            :on-click #(do (rf/dispatch [::events/set-gpu-type (-> % .-target .-value)])
                           (rf/dispatch [::events/set-gpu-type-key (-> % .-target .-value)]))}]
   [:input {:type "button"
            :class "btn"
            :value "GPU (Tesla P100)"
            :on-click #(do (rf/dispatch [::events/set-gpu-type (-> % .-target .-value)])
                           (rf/dispatch [::events/set-gpu-type-key (-> % .-target .-value)]))}]])

(defn choose-gpu-instance-type
  []
  (let [product-group-key @(rf/subscribe [::subs/product-group-key])
        gpu-type-key @(rf/subscribe [::subs/gpu-type-key])
        gpu-instance-type-list @(rf/subscribe [::subs/gpu-instance-type-list product-group-key gpu-type-key])]
    [:div "Choose an instance type:" [:strong @(rf/subscribe [::subs/instance-type])]
     (for [gpu-instance-type gpu-instance-type-list]
       ^{:key (str "gpu-instance-input-" gpu-instance-type)}
       [:input {:type "button"
                :value gpu-instance-type
                :class "btn"
                :on-click #(rf/dispatch [::events/set-instance-type (-> % .-target .-value)])}])]))

(defn choose-license
  []
  [:div "Do you require a Windows Instance?"
   [:input {:type "button"
            :class "btn"
            :value "Yes"
            :on-click #(rf/dispatch [::events/set-license (-> % .-target .-value)])}]
   [:input {:type "button"
            :class "btn"
            :value "No"
            :on-click #(rf/dispatch [::events/set-license (-> % .-target .-value)])}]])

(defn choose-local-storage-size
  []
  (let [min @(rf/subscribe [::subs/local-storage-min])
        max @(rf/subscribe [::subs/local-storage-max])]
    [:div
     [:label {:for "local-storage-size"} (str "Choose local storage size(Between " min " GB and " max " GB):" )
      [:strong (str @(rf/subscribe [::subs/local-storage-size]))]]
     [:input {:type "number"
              :name "local-storage-size"
              :min min
              :max max
              :on-change #(rf/dispatch [::events/set-local-storage-size (-> % .-target .-value)])}] " GB"]))

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

(defn choose-currency-type
  []
  [:div "Choose your currency: "
   (for [value ["CHF" "EUR" "USD"]]
     ^{:key (str "button-currency-" value)}
     [:input {:type "button" :class "btn-small" :value value :on-click #(rf/dispatch [::events/set-currency-type (-> % .-target .-value)])}])])

(defn generate-open-compute-key
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

(defn calculate-product-cost
  [selection currency-type open-compute license sos]
  (let [CHF->EUR 0.91
        CHF->USD 1.01
        currency-fn (case currency-type
                      "CHF" (fn [x] x)
                      "EUR" (fn [x] (* x CHF->EUR))
                      "USD" (fn [x] (* x CHF->USD)))
        license (if (= "Yes" (:windows-license? selection))
                  license
                  0)
        open-compute-key (generate-open-compute-key selection)
        open-compute-price (dc/decimal (get open-compute open-compute-key))]
    (println open-compute-key)
    (dc/+ open-compute-price
          (dc/decimal license)
          (dc/* (dc/decimal (:volume_data open-compute)) (dc/decimal (:local-storage-size selection))))))


(defn calculate-total
  [selection-list currency-type open-compute license sos]
  (let [currency-symbol (case currency-type
                       "CHF" (g-str/unescapeEntities "&#8355;")
                       "EUR" (g-str/unescapeEntities "&#8364;")
                       "USD" "$")]
    (if (= [] selection-list)
      (str currency-symbol 0)
      (str currency-symbol
           (apply +
                  (for [selection selection-list]
                             (calculate-product-cost selection currency-type open-compute license sos)))))))

(defn selection-row
  [selection]
  [:tr
   [:td (:product-group selection)]
   [:td (:instance-type selection)]
   [:td (:local-storage-size selection)]
   [:td (:windows-license? selection)]
   [:td (if (:gpu-type selection) (:gpu-type selection) "N/A")]
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
        license (rf/subscribe [::subs/licenses])
        open-compute (rf/subscribe [::subs/open-compute])
        sos (rf/subscribe [::subs/sos])]
    [:div
     [:div
      [choose-product-group]
      (if (= product-group-key :gpu-instances)
        [:div
         [choose-gpu-type]
         [choose-gpu-instance-type]]
        [choose-instance-type product-group-key])
      [choose-local-storage-size]
      [choose-license]
      [add-selection]]
     [:div {:class "price-calculator"}
      [choose-currency-type]
      [:p (str "Total price: " (calculate-total selection-list currency-type @open-compute @license @sos) "/hour")]]
     [selection-table]]))

