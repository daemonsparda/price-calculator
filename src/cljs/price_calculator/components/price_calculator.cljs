(ns price-calculator.components.price-calculator
  (:require [re-frame.core :as rf]
            [price-calculator.events :as events]
            [price-calculator.subs :as subs]))

(defn choose-product-group [product-group-list]
  [:div
   [:label {:for "product-group"} "First let's choose a product type: "
    [:strong (str @(rf/subscribe [::subs/product-group]))]]
   (for [product-group product-group-list]
     ^{:key (str "product-group-" product-group)}
     [:input {:type "button"
              :value product-group
              :class "btn"
              :on-click #(do (rf/dispatch [::events/set-product-group-key (-> % .-target .-value)])
                             (rf/dispatch [::events/set-product-group (-> % .-target .-value)]))}])])

(defn choose-instance-type
  [product-group-key instance-type-list]
  [:div
   [:label {:for "instance-type"} "Choose an instance type: "
    [:strong (str @(rf/subscribe [::subs/instance-type]))]]
   (for [instance-type instance-type-list]
     ^{:key (str "instance-input-" instance-type)}
     [:input {:type "button"
              :value instance-type
              :class "btn"
              :on-click #(do (rf/dispatch [::events/set-instance-type (-> % .-target .-value)])
                             (rf/dispatch [::events/set-local-storage-min product-group-key instance-type])
                             (rf/dispatch [::events/set-local-storage-max product-group-key instance-type]))}])])

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
  [instance-type-list]
  [:div "Choose an instance type:" [:strong @(rf/subscribe [::subs/instance-type])]
   (for [instance-type instance-type-list]
     ^{:key (str "gpu-instance-input-" instance-type)}
     [:input {:type "button"
              :value instance-type
              :class "btn"
              :on-click #(rf/dispatch [::events/set-instance-type (-> % .-target .-value)])}])])

(defn choose-license
  []
  [:div "Do you require a Windows License?"
   [:input {:type "button"
            :class "btn"
            :value "Yes"
            :on-click #(rf/dispatch [::events/set-license (-> % .-target .-value)])}]
   [:input {:type "button"
            :class "btn"
            :value "No"
            :on-click #(rf/dispatch [::events/set-license (-> % .-target .-value)])}]])

(defn choose-local-storage-size
  [min max]
  [:div
   [:label {:for "local-storage-size"} (str "Choose local storage size(Between " min " GB and " max " GB):" )
    [:strong (str @(rf/subscribe [::subs/local-storage-size]))]]
   [:input {:type "number"
            :name "local-storage-size"
            :min min
            :max max
            :on-change #(rf/dispatch [::events/set-local-storage-size (-> % .-target .-value)])}] " GB"])

(defn submit
  []
  (let [selection {:product-group @(rf/subscribe [::subs/product-group])
                   :instance-type @(rf/subscribe [::subs/instance-type])
                   :gpu-type @(rf/subscribe [::subs/gpu-type])
                   :local-storage-size @(rf/subscribe [::subs/local-storage-size])
                   :windows-license? @(rf/subscribe [::subs/windows-license?])}]
    [:input {:type "button"
             :value "Submit Selection"
             :class "btn-submit"
             :on-click #(rf/dispatch [::events/submit-selection selection])}]))

(defn price-calculator []
  (let [product-group-list (rf/subscribe [::subs/product-group-list])
        product-group-key (rf/subscribe [::subs/product-group-key])
        instance-type-list (rf/subscribe [::subs/instance-type-list @product-group-key])
        gpu-type-key (rf/subscribe [::subs/gpu-type-key])
        gpu-instance-type-list (rf/subscribe [::subs/gpu-instance-type-list @product-group-key @gpu-type-key])
        instance-type (rf/subscribe [::subs/instance-type])
        local-storage-min (rf/subscribe [::subs/local-storage-min])
        local-storage-max (rf/subscribe [::subs/local-storage-max])]
    [:div
     [:div
      [choose-product-group @product-group-list]
      (if (= product-group-key :gpu-instances)
        [:div
         [choose-gpu-type]
         [choose-gpu-instance-type @gpu-instance-type-list]]
        [choose-instance-type @product-group-key @instance-type-list]) ;; This should only show in the case standard-instaces is chosen
      [choose-local-storage-size @local-storage-min @local-storage-max]
      [choose-license]
      [submit]]
     [:div {:class "price-calculator"}
      [:p "Total price: $0/month"]]]))
