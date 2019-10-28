(ns price-calculator.utils.calculator
  (:require [camel-snake-kebab.core :as csk]
            [clojure.string :as str]
            [cljs.reader :refer [read-string]]
            [cljs.pprint :as pprint]))

(defn pprint-output
  [output]
  (pprint/cl-format nil "~,5f" output))

(defn generate-compute-key
  [selection]
  (when (seq selection)
    (keyword
     (str "running_" (case (:product-group selection)
                       "Standard Instances" ""
                       "Storage Optimized Instances" "storage_"
                       "GPU Instances" (if (= (:gpu-type selection) :gpu)
                                         "gpu_"
                                         "gpu2_")
                       :default "")
          (csk/->snake_case_string
           (str/lower-case (:instance-type selection)))))))

(defn calculate-compute-cost
  [instance-price license-win-price local-storage-price local-storage-size currency-fn]
  (let [instance-price (read-string instance-price)
        license-win-price (read-string license-win-price)
        local-storage-price (read-string local-storage-price)]
    (pprint-output
     (currency-fn
      (+ instance-price
         license-win-price
         (* local-storage-price local-storage-size))))))

;; TODO: These 2 need tests
;; Need to take into account the size of the snapshots
;; might be best to add to the compute resources
;; templates also have a size
;; Make sure template-size and snapshot sizes are stored as numbers and we gucci
(defn calculate-additional-features-cost
  [selection compute-pricing]
  (let [snapshot-price (* (apply + (:snapshot-sizes selection))
                          (:snapshot compute-pricing))
        eip-address-price (* (:eip-address selection)
                             (:eip_address compute-pricing))
        template-price (* (apply + (:template-sizes selection))
                          (:template compute-pricing))]
    (+ snapshot-price eip-address-price template-price)))

(defn calculate-object-storage-cost
  [selection object-storage-pricing]
  (* (:object-storage-size selection)
     (:storage_volume object-storage-pricing)))

(defn calculate-product-cost
  [selection currency-type compute-pricing license-win-price object-storage-pricing]
  (let [CHF->EUR 0.91
        CHF->USD 1.01
        currency-fn (case currency-type
                      "CHF" (fn [x] x)
                      "EUR" (fn [x] (* x CHF->EUR))
                      "USD" (fn [x] (* x CHF->USD)))]
    (case (:product-group selection)
      "Additional Features" (-> selection
                                (calculate-additional-features-cost compute-pricing)
                                currency-fn)
      "DNS Package" (case (:package selection)
                      "Small" 1
                      "Medium" 5
                      "Large" 25)
      "Object Storage" (-> selection
                           (calculate-object-storage-cost object-storage-pricing)                  
                           currency-fn)
      (let [compute-key (generate-compute-key selection)
            instance-price (get compute-pricing compute-key)
            license-win-price (if (= "Yes" (:windows-license? selection))
                                license-win-price
                                "0")
            local-storage-key (if (= "Storage Optimized Instances" (:product-group selection))
                                :volume_data
                                :volume)
            local-storage-price (local-storage-key compute-pricing)
            local-storage-size (:local-storage-size selection)]
        (read-string
         (calculate-compute-cost
          instance-price
          license-win-price
          local-storage-price
          local-storage-size
          currency-fn))))))


(defn calculate-total
  [selection-list currency-type compute-pricing license-win-price object-storage-pricing]
  (let [currency-symbol (case currency-type
                          "CHF" "₣"
                          "EUR" "€"
                          "USD" "$")]
    (if (empty? selection-list)
      (str currency-symbol "0.00000")
      (str currency-symbol
           (pprint-output
            (apply
             +
             (for [selection selection-list]
               (calculate-product-cost
                selection
                currency-type
                compute-pricing
                license-win-price
                object-storage-pricing))))))))
