(ns price-calculator.subs
  (:require
   [re-frame.core :as rf]
   [camel-snake-kebab.core :as csk]))

(rf/reg-sub
 ::name
 :name)

(rf/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

;; HTTP Requests
(rf/reg-sub
 ::http
 (fn [db _]
   (:http db)))

(rf/reg-sub
 ::license-win
 (fn [db _]
   (get-in db [:http :success :license-pricing-result :chf :license_win])))

(rf/reg-sub
 ::license-pricing
 (fn [db _]
   (get-in db [:http :success :license-pricing-result :chf])))

(rf/reg-sub
 ::compute-pricing
 (fn [db _]
   (get-in db [:http :success :compute-pricing-result :chf])))

(rf/reg-sub
 ::object-storage-pricing
 (fn [db _]
   (get-in db [:http :success :object-storage-pricing-result :chf])))

;; Options
(defn product-group-list
  [db _]
  (let [product-groups (:product-groups db)]
    [(get-in product-groups [:standard-instances :name])
     (get-in product-groups [:storage-optimized-instances :name])
     (get-in product-groups [:gpu-instances :name])
     (get-in product-groups [:additional-features :name])]))

(rf/reg-sub
 ::product-group-list
 product-group-list)

(defn instance-type-list
  [db [_ selected-product-group]]
  (case selected-product-group
    :gpu-instances
    (for [instance-type (get-in db [:product-groups selected-product-group :gpu2 :instance-types])]
      (:name instance-type))

    :storage-optimized-instances
    (for [instance-type (get-in db [:product-groups selected-product-group :instance-types])]
      (:name instance-type))

    :standard-instances
    (for [instance-type (get-in db [:product-groups selected-product-group :instance-types])]
      (:name instance-type))))

(rf/reg-sub
 ::instance-type-list
 instance-type-list)

(rf/reg-sub
 ::currency-type
 (fn [db _]
   (:currency-type db "CHF")))

;; Selection
(rf/reg-sub
 ::additional-features
 (fn [db _]
   (:additional-features db)))

(rf/reg-sub
 ::current-selection
 (fn [db _]
   (:current-selection db)))

(rf/reg-sub
 ::product-group
 (fn [db _]
   (get-in db [:current-selection :product-group] "Standard Instances")))

(rf/reg-sub
 ::product-group-key
 (fn [db _]
   (let [product-group-key (csk/->kebab-case-keyword @(rf/subscribe [::product-group]))]
     product-group-key)))

(rf/reg-sub
 ::instance-type
 (fn [db _]
   (get-in db [:current-selection :instance-type] "")))

(rf/reg-sub
 ::gpu-type
 (fn [db _]
   (get-in db [:current-selection :gpu-type])))

(rf/reg-sub
 ::gpu-type-key
 (fn [db _]
   (let [gpu-type-key (if (= @(rf/subscribe [::gpu-type]) "GPU2 (Tesla V100)") :gpu2 :gpu)]
     gpu-type-key)))

(rf/reg-sub
 ::local-storage-size
 (fn [db _]
   (get-in db [:current-selection :local-storage-size])))

(rf/reg-sub
 ::snapshot
 (fn [db _]
   (get-in db [:current-selection :snapshot])))

(rf/reg-sub
 ::snapshot-amount
 (fn [db _]
   (get-in db [:current-selection :snapshot-amount])))

(rf/reg-sub
 ::windows-license?
 (fn [db _]
   (get-in db [:current-selection :windows-license?])))

(defn local-storage-range [db [_ product-group-key instance-type]]
  (let [instance-types (if (= product-group-key :gpu-instances)
                         (get-in db [:product-groups product-group-key :gpu2 :instance-types])
                         (get-in db [:product-groups product-group-key :instance-types]))
        range (first (remove nil? (set (map #(when (= (:name %) instance-type)
                                               (get % :local-storage))
                                            instance-types))))]
    range))

(rf/reg-sub
 ::local-storage-range
 local-storage-range)

(rf/reg-sub
 ::selection-list
 (fn [db _]
   (:selection-list db)))

(rf/reg-sub
 ::dns-package
 (fn [db _]
   (get-in db [:current-selection :dns-package] "")))

(rf/reg-sub
 ::dns-package-price
 (fn [db _]
   (let [dns-package (get-in db [:current-selection :dns-package] "No")
         currency-type (get db :currency-type "CHF")
         currency-symbol (case currency-type
                           "CHF" "₣"
                           "EUR" "€"
                           "USD" "$")]
     (when-not (= "No" dns-package)
       (str "DNS package: " currency-symbol
            (if (= "EUR" currency-type)
              (case dns-package
                "Small" 1
                "Medium" 5
                "Large" 23)
              (case dns-package
                "Small" 1
                "Medium" 5
                "Large" 25))
            "/month")))))


(rf/reg-sub
 ::eip-address
 (fn [db _]
   (get-in db [:current-selection :eip-address] "")))

(rf/reg-sub
 ::eip-address-amount
 (fn [db _]
   (get-in db [:current-selection :eip-address-amount] "")))

(rf/reg-sub
 ::custom-template
 (fn [db _]
   (get-in db [:current-selection :custom-template] "")))

(rf/reg-sub
 ::custom-template-size
 (fn [db _]
   (get-in db [:current-selection :custom-template-size] "")))

(rf/reg-sub
 ::custom-template-zones
 (fn [db _]
   (get-in db [:current-selection :custom-template-zones] "")))

(rf/reg-sub
 ::object-storage
 (fn [db _]
   (get-in db [:current-selection :object-storage] "")))

(rf/reg-sub
 ::object-storage-size
 (fn [db _]
   (get-in db [:current-selection :object-storage-size] "")))
