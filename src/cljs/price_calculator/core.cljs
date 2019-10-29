(ns price-calculator.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as rf]
   [price-calculator.events :as events]
   [price-calculator.routes :as routes]
   [price-calculator.views :as views]
   [price-calculator.config :as config]
   [day8.re-frame.http-fx]))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn init []
  (routes/app-routes)
  (rf/dispatch-sync [::events/initialize-db])
  (rf/dispatch-sync [::events/fetch-license-pricing])
  (rf/dispatch-sync [::events/fetch-compute-pricing])
  (rf/dispatch-sync [::events/fetch-object-storage-pricing])

  (dev-setup)
  (mount-root))
