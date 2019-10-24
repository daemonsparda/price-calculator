(ns price-calculator.events
  (:require
   [re-frame.core :as re-frame]
   [camel-snake-kebab.core :as csk]
   [price-calculator.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]))


(re-frame/reg-event-db
 ::initialize-db
 (fn-traced
  [_ _]
  db/default-db))

(defn-traced set-active-panel
  [db [_ value]]
  (assoc db :active-panel value))

(re-frame/reg-event-db
 ::set-active-panel
 set-active-panel)

(defn-traced set-product-group
  [db [_ value]]
  (let [product-group-keyword (csk/->kebab-case-keyword value)]
    (assoc db :product-group-keyword product-group-keyword)
    (assoc-in db [:selection :product-group] value)))

(re-frame/reg-event-db
 ::set-product-group
 set-product-group)

(defn-traced set-product-group-key
  [db [_ value]]
  (let [product-group-key (csk/->kebab-case-keyword value)]
    (assoc db :product-group-key product-group-key)))

(re-frame/reg-event-db
 ::set-product-group-key
 set-product-group-key)

(defn-traced set-gpu-type
  [db [_ value]]
  (let [gpu-type-key (if (= value "GPU2 (Tesla V100)")
                       :gpu2
                       :gpu)]
    (assoc-in db [:selection :gpu-type] value)))

(re-frame/reg-event-db
 ::set-gpu-type
 set-gpu-type)

(defn-traced set-gpu-type-key
  [db [_ value]]
  (let [gpu-keyword (if (= value "GPU2 (Tesla V100)")
                      :gpu2
                      :gpu)]
    (assoc db :gpu-type-key gpu-keyword)))

(re-frame/reg-event-db
 ::set-gpu-type-key
 set-gpu-type-key)

(defn-traced set-instance-type
  [db [_ value]]
  (assoc-in db [:selection :instance-type] value))

(re-frame/reg-event-db
 ::set-instance-type
 set-instance-type)

(defn-traced set-local-storage-min
  [db [_ product-group-key instance-type]]
  (let [instance-types (get-in db [:product-groups product-group-key :instance-types])
        min (first (remove nil? (into #{} (map #(if (= (:name %) instance-type)
                                           (get-in % [:local-storage :min])
                                           nil) instance-types))))]
    
    (assoc db :local-storage-min min)))

(re-frame/reg-event-db
 ::set-local-storage-min
 set-local-storage-min)


(defn-traced set-local-storage-max
  [db [_ product-group-key instance-type]]
  (let [instance-types (get-in db [:product-groups product-group-key :instance-types])
        max (first (remove nil? (into #{} (map #(if (= (:name %) instance-type)
                                                  (get-in % [:local-storage :max])
                                                  nil) instance-types))))]
    (assoc db :local-storage-max max)))

(re-frame/reg-event-db
 ::set-local-storage-max
 set-local-storage-max)

(defn-traced set-local-storage-size
  [db [_ value]]
  (assoc-in db [:selection :local-storage-size] value))

(re-frame/reg-event-db
 ::set-local-storage-size
 set-local-storage-size)

(defn-traced set-license
  [db [_ value]]
  (assoc-in db [:selection :windows-license?] value))

(re-frame/reg-event-db
 ::set-license
 set-license)

(defn-traced submit-selection
  [db [_ value]]
  (let [selection-list (:selection-list db)]
       (assoc db :selection-list (conj selection-list value))))

(re-frame/reg-event-db
 ::submit-selection
 submit-selection)
