(ns price-calculator.events
  (:require
   [re-frame.core :as re-frame]
   [camel-snake-kebab.core :as csk]
   [price-calculator.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [ajax.core :as ajax]))


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
 ::fetch-licenses-success
 (fn-traced [db [_ result]]
            (assoc-in db [:http :licenses-result] (get-in result [:chf :license_win]))))

(re-frame/reg-event-db
 ::fetch-licenses-failure
 (fn-traced [db [_ result]]
            (assoc-in db [:http :failure :licenses-result] result)))

;; HTTP Requests
(re-frame/reg-event-fx
 ::fetch-licenses
 (fn-traced [{:keys [db]} _]
            {:db (assoc db :show-loading-screen true)
             :http-xhrio {:method :get
                          :uri "https://sos-de-muc-1.exo.io/exercises-pricing-data/licenses.json"
                          :timeout 8000
                          :response-format (ajax/json-response-format {:keywords? true})
                          :on-success [::fetch-licenses-success]
                          :on-failure [::fetch-licenses-failure]}}))

(re-frame/reg-event-db
 ::fetch-open-compute-success
 (fn-traced [db [_ result]]
            (assoc-in db [:http :open-compute-result] (:chf result))))

(re-frame/reg-event-db
 ::fetch-open-compute-failure
 (fn-traced [db [_ result]]
            (assoc-in db [:http :failure :open-compute-result] result)))

;; HTTP Requests
(re-frame/reg-event-fx
 ::fetch-open-compute
 (fn-traced [{:keys [db]} _]
            {:db (assoc db :show-loading-screen true)
             :http-xhrio {:method :get
                          :uri "https://sos-de-muc-1.exo.io/exercises-pricing-data/opencompute.json"
                          :timeout 8000
                          :response-format (ajax/json-response-format {:keywords? true})
                          :on-success [::fetch-open-compute-success]
                          :on-failure [::fetch-open-compute-failure]}}))

(re-frame/reg-event-db
 ::fetch-sos-success
 (fn-traced [db [_ result]]
            (assoc-in db [:http :sos-result] (:chf result))))

(re-frame/reg-event-db
 ::fetch-sos-failure
 (fn-traced [db [_ result]]
            (assoc-in db [:http :failure :sos-result] result)))

;; HTTP Requests
(re-frame/reg-event-fx
 ::fetch-sos
 (fn-traced [{:keys [db]} _]
            {:db (assoc db :show-loading-screen true)
             :http-xhrio {:method :get
                          :uri "https://sos-de-muc-1.exo.io/exercises-pricing-data/sos.json"
                          :timeout 8000
                          :response-format (ajax/json-response-format {:keywords? true})
                          :on-success [::fetch-sos-success]
                          :on-failure [::fetch-sos-failure]}}))

;; Selection Events

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

(defn-traced add-selection
  [db [_ value]]
  (let [selection-list (:selection-list db)]
       (assoc db :selection-list (conj selection-list value))))

(re-frame/reg-event-db
 ::add-selection
 add-selection)

(defn-traced set-currency-type
  [db [_ value]]
  (assoc db :currency-type value))

(re-frame/reg-event-db
 ::set-currency-type
 set-currency-type)
