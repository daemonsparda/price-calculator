(ns price-calculator.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

;; Options
(re-frame/reg-sub
 ::product-group-list
 (fn [db _]
   (let [product-groups (:product-groups db)]
    [(get-in product-groups [:standard-instances :name])
     (get-in product-groups [:storage-optimized-instances :name])
     (get-in product-groups [:gpu-instances :name])
     (get-in product-groups [:additional-features :name])])))

(re-frame/reg-sub
 ::instance-type-list
 (fn [db [_ selected-product-group]]
   (for [instance-type (get-in db [:product-groups selected-product-group :instance-types])]
     (:name instance-type))))

(re-frame/reg-sub
 ::gpu-instance-type-list
 (fn [db [_ selected-product-group gpu-type-key]]
   (for [instance-type (get-in db [:product-groups selected-product-group gpu-type-key :instance-types])]
     (:name instance-type))))

;; Selection
(re-frame/reg-sub
 ::product-group
 (fn [db _]
   (get-in db [:selection :product-group])))

(re-frame/reg-sub
 ::product-group-key
 (fn [db _]
   (:product-group-key db)))

(re-frame/reg-sub
 ::instance-type
 (fn [db _]
   (get-in db [:selection :instance-type])))

(re-frame/reg-sub
 ::gpu-type
 (fn [db _]
   (get-in db [:selection :gpu-type])))

(re-frame/reg-sub
 ::gpu-type-key
 (fn [db _]
   (:gpu-type-key db)))

(re-frame/reg-sub
 ::local-storage-size
 (fn [db _]
   (get-in db [:selection :local-storage-size])))

(re-frame/reg-sub
 ::windows-license?
 (fn [db _]
   (get-in db [:selection :windows-license?])))

(re-frame/reg-sub
 ::local-storage-min
 (fn [db _]
   (:local-storage-min db)))

(re-frame/reg-sub
 ::local-storage-max
 (fn [db _]
   (:local-storage-max db)))
