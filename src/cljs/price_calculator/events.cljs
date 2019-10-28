(ns price-calculator.events
  (:require
   [re-frame.core :as re-frame]
   [price-calculator.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [ajax.core :as ajax]))

(defn-traced initialize-db
  [_ _]
  db/default-db)

(re-frame/reg-event-db
 ::initialize-db
 initialize-db)

(defn-traced set-active-panel
  [db [_ value]]
  (assoc db :active-panel value))

(re-frame/reg-event-db
 ::set-active-panel
 set-active-panel)

;; HTTP Request Events

(defn-traced fetch-license-pricing-success
  [db [_ result]]
  (assoc-in db [:http :success :license-pricing-result] result))

(re-frame/reg-event-db
 ::fetch-license-pricing-success
 fetch-license-pricing-success)

(defn-traced fetch-license-pricing-failure
 [db [_ result]]
  (assoc-in db [:http :failure :license-pricing-error] result))

(re-frame/reg-event-db
 ::fetch-license-pricing-failure
 fetch-license-pricing-failure)

(defn-traced fetch-license-pricing
 [{:keys [db]} _]
 {:db (assoc db :show-loading-screen true)
  :http-xhrio {:method :get
               :uri "https://sos-de-muc-1.exo.io/exercises-pricing-data/licenses.json"
               :timeout 8000
               :response-format (ajax/json-response-format {:keywords? true})
               :on-success [::fetch-license-pricing-success]
               :on-failure [::fetch-license-pricing-failure]}})

(re-frame/reg-event-fx
 ::fetch-license-pricing
 fetch-license-pricing)

(defn-traced fetch-compute-pricing-success
  [db [_ result]]
  (assoc-in db [:http :success :compute-pricing-result] result))

(re-frame/reg-event-db
 ::fetch-compute-pricing-success
 fetch-compute-pricing-success)

(defn-traced fetch-compute-pricing-failure
  [db [_ result]]
  (assoc-in db [:http :failure :compute-pricing-error] result))

(re-frame/reg-event-db
 ::fetch-compute-pricing-failure
 fetch-compute-pricing-failure)

(defn-traced fetch-compute-pricing
 [{:keys [db]} _]
 {:db (assoc db :show-loading-screen true)
  :http-xhrio {:method :get
               :uri "https://sos-de-muc-1.exo.io/exercises-pricing-data/opencompute.json"
               :timeout 8000
               :response-format (ajax/json-response-format {:keywords? true})
               :on-success [::fetch-compute-pricing-success]
               :on-failure [::fetch-compute-pricing-failure]}})

(re-frame/reg-event-fx
 ::fetch-compute-pricing
 fetch-compute-pricing)


(defn-traced fetch-object-storage-pricing-success
 [db [_ result]]
  (assoc-in db [:http :success :object-storage-pricing-result] result))

(re-frame/reg-event-db
 ::fetch-object-storage-pricing-success
 fetch-object-storage-pricing-success)

(defn-traced fetch-object-storage-pricing-failure
  [db [_ result]]
  (assoc-in db [:http :failure :object-storage-pricing-error] result))

(re-frame/reg-event-db
 ::fetch-object-storage-pricing-failure
 fetch-object-storage-pricing-failure)

(defn-traced fetch-object-storage-pricing
  [{:keys [db]} _]
  {:db (assoc db :show-loading-screen true)
   :http-xhrio {:method :get
                :uri "https://sos-de-muc-1.exo.io/exercises-pricing-data/sos.json
"
                :timeout 8000
                :response-format (ajax/json-response-format {:keywords? true})
                :on-success [::fetch-object-storage-pricing-success]
                :on-failure [::fetch-object-storage-pricing-failure]}})

(re-frame/reg-event-fx
 ::fetch-object-storage-pricing
 fetch-object-storage-pricing)

;; Selection Events
;; convert event to an fx event
(defn-traced set-product-group
  [db [_ value]]
  (if (= "Additional Features" value)
    (doall
      
     (assoc db :additional-features (:additional-features db))
     (assoc-in db [:current-selection :product-group] value)
     )
    (assoc-in db [:current-selection :product-group] value)))

(re-frame/reg-event-db
 ::set-product-group
 set-product-group)

(defn-traced set-gpu-type
  [db [_ value]]
  (assoc-in db [:current-selection :gpu-type] value))

(re-frame/reg-event-db
 ::set-gpu-type
 set-gpu-type)

(defn-traced set-instance-type
  [db [_ value]]
  (assoc-in db [:current-selection :instance-type] value))

(re-frame/reg-event-db
 ::set-instance-type
 set-instance-type)

(defn-traced set-local-storage-size
  [db [_ value]]
  (assoc-in db [:current-selection :local-storage-size] value))

(re-frame/reg-event-db
 ::set-local-storage-size
 set-local-storage-size)

(defn-traced set-snapshot
  [db [_ value]]
  (assoc-in db [:current-selection :snapshot] value))

(re-frame/reg-event-db
 ::set-snapshot
 set-snapshot)

(defn-traced set-snapshot-amount
  [db [_ value]]
  (assoc-in db [:current-selection :snapshot-amount] value))

(re-frame/reg-event-db
 ::set-snapshot-amount
 set-snapshot-amount)

(defn-traced set-license
  [db [_ value]]
  (assoc-in db [:current-selection :windows-license?] value))

(re-frame/reg-event-db
 ::set-license
 set-license)

(defn-traced set-currency-type
  [db [_ value]]
  (assoc db :currency-type value))

(re-frame/reg-event-db
 ::set-currency-type
 set-currency-type)

(defn-traced add-selection
  [db [_ value]]
  (let [selection-list (:selection-list db)]
    (if (= "Additional Features" (:product-group value))
      (assoc db :additional-features value)
      (assoc db :selection-list (conj selection-list value)))))

(re-frame/reg-event-db
 ::add-selection
 add-selection)

(defn-traced set-dns-package
  [db [_ value]]
  (assoc-in db [:current-selection :dns-package] value))

(re-frame/reg-event-db
 ::set-dns-package
 set-dns-package)

(defn-traced set-eip-address
  [db [_ value]]
  (assoc-in db [:current-selection :eip-address] value))

(re-frame/reg-event-db
 ::set-eip-address
 set-eip-address)
 
(defn-traced set-eip-address-amount
  [db [_ value]]
  (assoc-in db [:current-selection :eip-address-amount] value))

(re-frame/reg-event-db
 ::set-eip-address-amount
 set-eip-address-amount)

(defn-traced set-custom-template
  [db [_ value]]
  (assoc-in db [:current-selection :custom-template] value))

(re-frame/reg-event-db
 ::set-custom-template
 set-custom-template)

(defn-traced set-custom-template-size
  [db [_ value]]
  (assoc-in db [:current-selection :custom-template-size] value))

(re-frame/reg-event-db
 ::set-custom-template-size
 set-custom-template-size)

(defn-traced set-custom-template-zones
  [db [_ value]]
  (assoc-in db [:current-selection :custom-template-zones] value))

(re-frame/reg-event-db
 ::set-custom-template-zones
 set-custom-template-zones)

(defn-traced set-object-storage
  [db [_ value]]
  (assoc-in db [:current-selection :object-storage] value))

(re-frame/reg-event-db
 ::set-object-storage
 set-object-storage)

(defn-traced set-object-storage-size
  [db [_ value]]
  (assoc-in db [:current-selection :object-storage-size] value))

(re-frame/reg-event-db
 ::set-object-storage-size
 set-object-storage-size)
