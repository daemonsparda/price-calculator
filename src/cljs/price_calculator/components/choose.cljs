(ns price-calculator.components.choose
  (:require [re-frame.core :as rf]
            [price-calculator.events :as events]
            [price-calculator.subs :as subs]))


(defn product-group [product-group-list]
  (let [product-group-list @(rf/subscribe [::subs/product-group-list])]
    [:div
     [:label {:for "product-group"} "First let's choose a product type: "
      [:strong (str @(rf/subscribe [::subs/product-group]))]]
     (for [product-group product-group-list]
       ^{:key (str "product-group-" product-group)}
       [:input {:type "button"
                :value product-group
                :class "btn"
                :on-click #(rf/dispatch [::events/set-product-group (-> % .-target .-value)])}])]))

(defn instance-type
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
                :on-click #(rf/dispatch [::events/set-instance-type (-> % .-target .-value)])}])]))

(defn gpu-type
  []
  [:div "Choose the type of GPU:" [:strong @(rf/subscribe [::subs/gpu-type])]
   [:input {:type "button"
            :class "btn"
            :value "GPU2 (Tesla V100)"
            :on-click #(rf/dispatch [::events/set-gpu-type (-> % .-target .-value)])}]
   [:input {:type "button"
            :class "btn"
            :value "GPU (Tesla P100)"
            :on-click #(rf/dispatch [::events/set-gpu-type (-> % .-target .-value)])}]])

(defn gpu-instance-type
  []
  (let [product-group-key @(rf/subscribe [::subs/product-group-key])
        gpu-type-key @(rf/subscribe [::subs/gpu-type-key])
        gpu-instance-type-list @(rf/subscribe [::subs/instance-type-list product-group-key gpu-type-key])]
    [:div "Choose an instance type:" [:strong @(rf/subscribe [::subs/instance-type])]
     (for [gpu-instance-type gpu-instance-type-list]
       ^{:key (str "gpu-instance-input-" gpu-instance-type)}
       [:input {:type "button"
                :value gpu-instance-type
                :class "btn"
                :on-click #(rf/dispatch [::events/set-instance-type (-> % .-target .-value)])}])]))

(defn license
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

(defn local-storage-size
  []
  (let [product-group-key @(rf/subscribe [::subs/product-group-key])
        instance-type @(rf/subscribe [::subs/instance-type])
        range @(rf/subscribe [::subs/local-storage-range product-group-key instance-type])
        min (:min range)
        max (:max range)]
    [:div
     [:label {:for "local-storage-size"} (str "Choose local storage size(Between " min " GB and " max " GB):" )
      [:strong (str @(rf/subscribe [::subs/local-storage-size]))]]
     [:input {:type "number"
              :name "local-storage-size"
              :min min
              :max max
              :on-change #(rf/dispatch [::events/set-local-storage-size (-> % .-target .-value)])}] " GB"]))

(defn currency-type
  []
  [:div "Choose your currency: "
   (for [value ["CHF" "EUR" "USD"]]
     ^{:key (str "button-currency-" value)}
     [:input {:type "button"
              :class "btn-small"
              :value value
              :on-click #(rf/dispatch [::events/set-currency-type (-> % .-target .-value)])}])])
