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
  [instance-price
   license-win-price
   local-storage-price
   local-storage-size
   currency-fn
   snapshot-price
   snapshot
   snapshot-amount]
  (let [instance-price (read-string instance-price)
        license-win-price (read-string license-win-price)
        local-storage-price (read-string local-storage-price)
        snapshot-price (read-string snapshot-price)
        snapshot-price (if (= "Yes" snapshot)
                         (* snapshot-amount snapshot-price)
                         0)]
    (pprint-output
     (currency-fn
      (+ instance-price
         license-win-price
         snapshot-price
         (* local-storage-price local-storage-size))))))

(defn calculate-eip-address-price
  [additional-features compute-pricing]
  (if (= "Yes" (:eip-address additional-features))
    (* (:eip-address-amount additional-features)
       (read-string (:eip_address compute-pricing)))
    0))

(defn calculate-template-price
  [additional-features compute-pricing]
  (if (= "Yes" (:custom-template additional-features))
    (* (:custom-template-zones additional-features)
       (:custom-template-size additional-features)
       (read-string (:template compute-pricing)))
    0))

(defn calculate-object-storage-price
  [additional-features object-storage-pricing]
  (if (= "Yes" (:object-storage additional-features))
    (* (:object-storage-size additional-features)
       (read-string (:storage_volume object-storage-pricing)))
    0))

(defn calculate-additional-features-cost
  [additional-features compute-pricing object-storage-pricing currency-fn]
  (let [eip-address-price (calculate-eip-address-price
                           additional-features
                           compute-pricing)
        template-price (calculate-template-price
                        additional-features
                        compute-pricing)
        object-storage-price (calculate-object-storage-price
                              additional-features
                              object-storage-pricing)]
    (read-string
     (pprint-output
      (currency-fn (+ eip-address-price template-price object-storage-price))))))

(defn calculate-instance-cost
  [selection compute-pricing license-win-price currency-fn]
  (let [compute-key (generate-compute-key selection)
        instance-price (get compute-pricing compute-key)
        license-win-price (if (= "Yes" (:windows-license? selection))
                            license-win-price
                            "0")
        local-storage-key (if (= "Storage Optimized Instances"
                                 (:product-group selection))
                            :volume_data
                            :volume)
        local-storage-price (local-storage-key compute-pricing)
        local-storage-size (:local-storage-size selection)
        snapshot-price (:snapshot compute-pricing)
        snapshot (:snapshot selection)
        snapshot-amount (:snapshot-amount selection)]
    (read-string
     (calculate-compute-cost
      instance-price
      license-win-price
      local-storage-price
      local-storage-size
      currency-fn
      snapshot-price
      snapshot
      snapshot-amount))))

(defn calculate-product-cost
  [selection
   currency-type
   compute-pricing
   license-win-price
   object-storage-pricing]
  (let [CHF->EUR 0.91
        CHF->USD 1.01
        currency-fn (case currency-type
                      "CHF" (fn [x] x)
                      "EUR" (fn [x] (* x CHF->EUR))
                      "USD" (fn [x] (* x CHF->USD)))]
    (if (= (:product-group selection) "Additional Features") 
      (calculate-additional-features-cost
       selection
       compute-pricing
       object-storage-pricing
       currency-fn)
      (calculate-instance-cost
       selection
       compute-pricing
       license-win-price
       currency-fn))))

(defn calculate-total
  [selection-list
   additional-features
   currency-type
   compute-pricing
   license-win-price
   object-storage-pricing]
  (let [currency-symbol (case currency-type
                          "CHF" "₣"
                          "EUR" "€"
                          "USD" "$")
        selection-list (conj selection-list additional-features)]
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
