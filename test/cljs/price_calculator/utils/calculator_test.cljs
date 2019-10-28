(ns price-calculator.utils.calculator-test
  (:require [price-calculator.utils.calculator :as sut]
            [cljs.test :refer-macros [deftest is testing run-tests]]))

(deftest generate-compute-key-empty-selection-test
  (testing "Ensure nothing happens if selection is empty."
    (let [sut sut/generate-compute-key
          selection {}
          expected nil]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-std-micro-test
  (testing "MICRO"
    (let [sut sut/generate-compute-key
          selection {:product-group "Standard Instances"
                     :instance-type "MICRO"}
          expected :running_micro]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-std-tiny-test
  (testing "TINY"
    (let [sut sut/generate-compute-key
          selection {:product-group "Standard Instances"
                     :instance-type "TINY"}
          expected :running_tiny]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-std-small-test
  (testing "SMALL"
        (let [sut sut/generate-compute-key
              selection {:product-group "Standard Instances"
                         :instance-type "SMALL"}
              expected :running_small]
          (is (= expected (sut selection))))))

(deftest generate-compute-key-std-medium-test
  (testing "MEDIUM"
    (let [sut sut/generate-compute-key
          selection {:product-group "Standard Instances"
                     :instance-type "MEDIUM"}
          expected :running_medium]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-std-large-test
  (testing "LARGE"
    (let [sut sut/generate-compute-key
          selection {:product-group "Standard Instances"
                     :instance-type "LARGE"}
          expected :running_large]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-std-extra-large-test
  (testing "EXTRA-LARGE"
    (let [sut sut/generate-compute-key
          selection {:product-group "Standard Instances"
                     :instance-type "EXTRA-LARGE"}
          expected :running_extra_large]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-std-huge-test
  (testing "HUGE"
    (let [sut sut/generate-compute-key
          selection {:product-group "Standard Instances"
                     :instance-type "HUGE"}
          expected :running_huge]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-std-mega-test
  (testing "MEGA"
    (let [sut sut/generate-compute-key
          selection {:product-group "Standard Instances"
                     :instance-type "MEGA"}
          expected :running_mega]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-std-titan-test
  (testing "TITAN"
    (let [sut sut/generate-compute-key
          selection {:product-group "Standard Instances"
                     :instance-type "TITAN"}
          expected :running_titan]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-std-jumbo-test
  (testing "JUMBO"
    (let [sut sut/generate-compute-key
          selection {:product-group "Standard Instances"
                     :instance-type "JUMBO"}
          expected :running_jumbo]
      (is (= expected (sut selection))))))
  
(deftest generate-compute-key-storage-extra-large-test 
  (testing "EXTRA-LARGE"
    (let [sut sut/generate-compute-key
          selection {:product-group "Storage Optimized Instances"
                     :instance-type "EXTRA-LARGE"}
          expected :running_storage_extra_large]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-storage-huge-test
  (testing "HUGE"
    (let [sut sut/generate-compute-key
          selection {:product-group "Storage Optimized Instances"
                     :instance-type "HUGE"}
          expected :running_storage_huge]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-storage-mega-test
  (testing "MEGA"
    (let [sut sut/generate-compute-key
          selection {:product-group "Storage Optimized Instances"
                     :instance-type "MEGA"}
          expected :running_storage_mega]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-storage-titan-test
  (testing "TITAN"
    (let [sut sut/generate-compute-key
          selection {:product-group "Storage Optimized Instances"
                     :instance-type "TITAN"}
          expected :running_storage_titan]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-storage-jumbo-test
  (testing "JUMBO"
    (let [sut sut/generate-compute-key
          selection {:product-group "Storage Optimized Instances"
                     :instance-type "JUMBO"}
          expected :running_storage_jumbo]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-gpu2-small-test
  (testing "SMALL"
    (let [sut sut/generate-compute-key
          selection {:product-group "GPU Instances"
                     :instance-type "SMALL"
                     :gpu-type :gpu2}
          expected :running_gpu2_small]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-gpu2-medium-test
  (testing "MEDIUM"
    (let [sut sut/generate-compute-key
          selection {:product-group "GPU Instances"
                     :instance-type "MEDIUM"
                     :gpu-type :gpu2}
          expected :running_gpu2_medium]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-gpu2-large-test
  (testing "LARGE"
    (let [sut sut/generate-compute-key
          selection {:product-group "GPU Instances"
                     :instance-type "LARGE"
                     :gpu-type :gpu2}
          expected :running_gpu2_large]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-gpu2-huge-test
  (testing "HUGE"
    (let [sut sut/generate-compute-key
          selection {:product-group "GPU Instances"
                     :instance-type "HUGE"
                     :gpu-type :gpu2}
          expected :running_gpu2_huge]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-gpu-small-test
  (testing "SMALL"
    (let [sut sut/generate-compute-key
          selection {:product-group "GPU Instances"
                     :instance-type "SMALL"
                     :gpu-type :gpu}
          expected :running_gpu_small]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-gpu-medium-test
  (testing "MEDIUM"
    (let [sut sut/generate-compute-key
          selection {:product-group "GPU Instances"
                     :instance-type "MEDIUM"
                     :gpu-type :gpu}
          expected :running_gpu_medium]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-gpu-large-test
  (testing "LARGE"
    (let [sut sut/generate-compute-key
          selection {:product-group "GPU Instances"
                     :instance-type "LARGE"
                     :gpu-type :gpu}
          expected :running_gpu_large]
      (is (= expected (sut selection))))))

(deftest generate-compute-key-gpu-huge-test
  (testing "HUGE"
    (let [sut sut/generate-compute-key
          selection {:product-group "GPU Instances"
                     :instance-type "HUGE"
                     :gpu-type :gpu}
          expected :running_gpu_huge]
      (is (= expected (sut selection))))))

(deftest calculate-compute-cost-std-micro-windows-10GB-test
  (testing "Standard Instance: MICRO with windows license with 10GB of storage."
    (let [sut sut/calculate-compute-cost
          instance-price "0.00694000"
          license-win-price "0.01111000"
          local-storage-price "0.00014000"
          local-storage-size 10
          expected "0.01945"
          currency-fn (fn [x] x)]
      (is (= expected (sut instance-price
                           license-win-price
                           local-storage-price
                           local-storage-size
                           currency-fn))))))

(deftest calculate-compute-cost-std-micro-windows-100GB-test
  (testing "Standard Instance: MICRO with windows license with 100GB of storage."
    (let [sut sut/calculate-compute-cost
          instance-price "0.00694000"
          license-win-price "0.01111000"
          local-storage-price "0.00014000"
          local-storage-size 100
          expected "0.03205"
          currency-fn (fn [x] x)]
      (is (= expected (sut instance-price
                           license-win-price
                           local-storage-price
                           local-storage-size
                           currency-fn))))))

(deftest calculate-compute-cost-storage-jumbo-no-windows-20TB-test
  (testing "Storage Optimized Instance: JUMBO without windows license with 20TB of storage."
    (let [sut sut/calculate-compute-cost
          instance-price "1.77778000"
          license-win-price "0"
          local-storage-price "0.00006000"
          local-storage-size 20000
          expected "2.97778"
          currency-fn (fn [x] x)]
      (is (= expected (sut instance-price
                           license-win-price
                           local-storage-price
                           local-storage-size
                           currency-fn))))))

(deftest calculate-compute-cost-gpu-huge-no-windows-1600GB-test
  (testing "GPU Instance: HUGE without windows license with 1.6TB of storage."
    (let [sut sut/calculate-compute-cost
          instance-price "3.31944000"
          license-win-price "0"
          local-storage-price "0.00014000"
          local-storage-size 1600
          expected "3.54344"
          currency-fn (fn [x] x)]
      (is (= expected (sut instance-price
                           license-win-price
                           local-storage-price
                           local-storage-size
                           currency-fn))))))

(deftest calculate-additional-features-cost-test
  (testing "Ensure that additional features cost is properly calculated"
    (let [sut sut/calculate-additional-features-cost
          selection {:snapshot-sizes [100 200 200 235 1600]
                     :eip-address 3
                     :template-sizes [10 15]}
          compute-pricing {:snapshot "0.1"
                           :eip_address "0.1"
                           :template "0.1"}
          expected 236.3]
      (is (= expected (sut selection compute-pricing))))))

(deftest calculate-object-storage-cost-test
  (testing "Ensure that object storage cost is properly calculated"
    (let [sut sut/calculate-object-storage-cost
          selection {:object-storage-size 1000}
          object-storage-pricing {:storage_volume "0.00002750"}
          expected 0.0275]
      (is (= expected (sut selection object-storage-pricing))))))

(deftest calculate-product-cost-std-micro-windows-10GB-CHF-test
  (testing "Standard Instance: MICRO with windows license with 10GB of storage in CHF."
    (let [sut sut/calculate-product-cost
          selection {:product-group "Standard Instances"
                     :instance-type "MICRO"
                     :local-storage-size 10
                     :windows-license? "Yes"}
          currency-type "CHF"
          compute-pricing {:running_micro "0.00694000"
                           :volume "0.00014000"}
          license-win-price "0.01111000"
          expected 0.01945]
      (is (= expected (sut selection
                           currency-type
                           compute-pricing
                           license-win-price))))))

(deftest calculate-product-cost-std-micro-windows-10GB-EUR-test
  (testing "Standard Instance: MICRO with windows license with 10GB of storage in EUR."
    (let [sut sut/calculate-product-cost
          selection {:product-group "Standard Instances"
                     :instance-type "MICRO"
                     :local-storage-size 10
                     :windows-license? "Yes"}
          currency-type "EUR"
          compute-pricing {:running_micro "0.00694000"
                           :volume "0.00014000"}
          license-win-price "0.01111000"
          expected 0.01770]
      (is (= expected (sut selection
                           currency-type
                           compute-pricing
                           license-win-price))))))

(deftest calculate-product-cost-std-micro-windows-10GB-USD-test
  (testing "Standard Instance: MICRO with windows license with 10GB of storage in USD."
    (let [sut sut/calculate-product-cost
          selection {:product-group "Standard Instances"
                     :instance-type "MICRO"
                     :local-storage-size 10
                     :windows-license? "Yes"}
          currency-type "USD"
          compute-pricing {:running_micro "0.00694000"
                           :volume "0.00014000"}
          license-win-price "0.01111000"
          expected 0.01964]
      (is (= expected (sut selection
                           currency-type
                           compute-pricing
                           license-win-price))))))

(deftest calculate-product-cost-std-micro-windows-100GB-CHF-test
  (testing "Standard Instance: MICRO with windows license with 100GB of storage in CHF."
    (let [sut sut/calculate-product-cost
          selection {:product-group "Standard Instances"
                     :instance-type "MICRO"
                     :local-storage-size 100
                     :windows-license? "Yes"}
          currency-type "CHF"
          compute-pricing {:running_micro "0.00694000"
                           :volume "0.00014000"}
          license-win-price "0.01111000"
          expected 0.03205]
      (is (= expected (sut selection
                           currency-type
                           compute-pricing
                           license-win-price))))))

(deftest calculate-product-cost-std-micro-windows-100GB-EUR-test
  (testing "Standard Instance: MICRO with windows license with 100GB of storage in EUR."
    (let [sut sut/calculate-product-cost
          selection {:product-group "Standard Instances"
                     :instance-type "MICRO"
                     :local-storage-size 100
                     :windows-license? "Yes"}
          currency-type "EUR"
          compute-pricing {:running_micro "0.00694000"
                           :volume "0.00014000"}
          license-win-price "0.01111000"
          expected 0.02917]
      (is (= expected (sut selection
                           currency-type
                           compute-pricing
                           license-win-price))))))

(deftest calculate-product-cost-std-micro-windows-100GB-USD-test
  (testing "Standard Instance: MICRO with windows license with 100GB of storage in USD."
    (let [sut sut/calculate-product-cost
          selection {:product-group "Standard Instances"
                     :instance-type "MICRO"
                     :local-storage-size 100
                     :windows-license? "Yes"}
          currency-type "USD"
          compute-pricing {:running_micro "0.00694000"
                           :volume "0.00014000"}
          license-win-price "0.01111000"
          expected 0.03237]
      (is (= expected (sut selection
                           currency-type
                           compute-pricing
                           license-win-price))))))

(deftest calculate-product-cost-storage-jumbo-no-windows-20TB-CHF-test
  (testing "Storage Optimized Instance: JUMBO without windows license with 20TB of storage in CHF."
    (let [sut sut/calculate-product-cost
          selection {:product-group "Storage Optimized Instances"
                     :instance-type "JUMBO"
                     :local-storage-size 20000
                     :windows-license? "No"}
          currency-type "CHF"
          compute-pricing {:running_storage_jumbo "1.77778000"
                           :volume_data "0.00006000"}
          license-win-price "0.01111000"
          expected 2.97778]
      (is (= expected (sut selection
                           currency-type
                           compute-pricing
                           license-win-price))))))

(deftest calculate-product-cost-storage-jumbo-no-windows-20TB-EUR-test
  (testing "Storage Optimized Instance: JUMBO without windows license with 20TB of storage in EUR."
    (let [sut sut/calculate-product-cost
          selection {:product-group "Storage Optimized Instances"
                     :instance-type "JUMBO"
                     :local-storage-size 20000
                     :windows-license? "No"}
          currency-type "EUR"
          compute-pricing {:running_storage_jumbo "1.77778000"
                           :volume_data "0.00006000"}
          license-win-price "0.01111000"
          expected 2.70978]
      (is (= expected (sut selection
                           currency-type
                           compute-pricing
                           license-win-price))))))

(deftest calculate-product-cost-storage-jumbo-no-windows-20TB-USD-test
  (testing "Storage Optimized Instance: JUMBO without windows license with 20TB of storage in USD."
    (let [sut sut/calculate-product-cost
          selection {:product-group "Storage Optimized Instances"
                     :instance-type "JUMBO"
                     :local-storage-size 20000
                     :windows-license? "No"}
          currency-type "USD"
          compute-pricing {:running_storage_jumbo "1.77778000"
                           :volume_data "0.00006000"}
          license-win-price "0.01111000"
          expected 3.00756]
      (is (= expected (sut selection
                           currency-type
                           compute-pricing
                           license-win-price))))))

(deftest calculate-product-cost-gpu-huge-no-windows-1600GB-CHF-test
  (testing "GPU Instance: HUGE without windows license with 1.6TB of storage in CHF."
    (let [sut sut/calculate-product-cost
          selection {:product-group "GPU Instances"
                     :instance-type "HUGE"
                     :local-storage-size 1600
                     :windows-license? "No"
                     :gpu-type :gpu2}
          currency-type "CHF"
          compute-pricing {:running_gpu2_huge "3.31944000"
                           :volume "0.00014000"}
          license-win-price "0.01111000"
          expected 3.54344]
      (is (= expected (sut selection
                           currency-type
                           compute-pricing
                           license-win-price))))))

(deftest calculate-product-cost-gpu-huge-no-windows-1600GB-EUR-test
  (testing "GPU Instance: HUGE without windows license with 1.6TB of storage in EUR."
    (let [sut sut/calculate-product-cost
          selection {:product-group "GPU Instances"
                     :instance-type "HUGE"
                     :local-storage-size 1600
                     :windows-license? "No"
                     :gpu-type :gpu2}
          currency-type "EUR"
          compute-pricing {:running_gpu2_huge "3.31944000"
                           :volume "0.00014000"}
          license-win-price "0.01111000"
          expected 3.22453]
      (is (= expected (sut selection
                           currency-type
                           compute-pricing
                           license-win-price))))))

(deftest calculate-product-cost-gpu-huge-no-windows-1600GB-USD-test
  (testing "GPU Instance: HUGE without windows license with 1.6TB of storage in USD."
    (let [sut sut/calculate-product-cost
          selection {:product-group "GPU Instances"
                     :instance-type "HUGE"
                     :local-storage-size 1600
                     :windows-license? "No"
                     :gpu-type :gpu2}
          currency-type "USD"
          compute-pricing {:running_gpu2_huge "3.31944000"
                           :volume "0.00014000"}
          license-win-price "0.01111000"
          expected 3.57887]
      (is (= expected (sut selection
                           currency-type
                           compute-pricing
                           license-win-price))))))

(deftest calculate-total-no-selections-CHF-test
  (testing "No items in selection list in CHF."
    (let [sut sut/calculate-total
          selection-list [{}]
          currency-type "CHF"
          compute-pricing {}
          license-win-price "0.01111000"
          object-storage-pricing {:storage_volume "0.00002750"
                                  :storage_traffic "0.02000000"}
          expected "₣0.00000"]
      (is (= expected (sut selection-list
                           currency-type
                           compute-pricing
                           license-win-price
                           object-storage-pricing))))))

(deftest calculate-total-no-selections-EUR-test
  (testing "No items in selection list in EUR."
    (let [sut sut/calculate-total
          selection-list []
          currency-type "EUR"
          compute-pricing {}
          license-win-price "0.01111000"
          object-storage-pricing {:storage_volume "0.00002750"
                                  :storage_traffic "0.02000000"}
          expected "€0.00000"]
      (is (= expected (sut selection-list
                           currency-type
                           compute-pricing
                           license-win-price
                           object-storage-pricing))))))

(deftest calculate-total-no-selections-USD-test
  (testing "No items in selection list in USD."
    (let [sut sut/calculate-total
          selection-list []
          currency-type "USD"
          compute-pricing {}
          license-win-price "0.01111000"
          object-storage-pricing {:storage_volume "0.00002750"
                                  :storage_traffic "0.02000000"}
          expected "$0.00000"]
      (is (= expected (sut selection-list
                           currency-type
                           compute-pricing
                           license-win-price
                           object-storage-pricing))))))

(deftest calculate-total-multiple-selections-test
  (testing "Multiple items in selection list."
    (let [sut sut/calculate-total
          selection-list [{:product-group "Standard Instances"
                           :instance-type "MICRO"
                           :local-storage-size 10
                           :windows-license? "Yes"}
                          {:product-group "Standard Instances"
                           :instance-type "MICRO"
                           :local-storage-size 100
                           :windows-license? "Yes"}
                          {:product-group "Storage Optimized Instances"
                           :instance-type "JUMBO"
                           :local-storage-size 20000
                           :windows-license? "No"}
                          {:product-group "GPU Instances"
                           :instance-type "HUGE"
                           :local-storage-size 1600
                           :windows-license? "No"
                           :gpu-type :gpu2}]
          currency-type "CHF"
          compute-pricing {:running_micro "0.00694000"
                           :running_storage_jumbo "1.77778000"
                           :running_gpu2_huge "3.31944000"
                           :volume "0.00014000"
                           :volume_data "0.00006000"}
          license-win-price "0.01111000"
          object-storage-pricing {:storage_volume "0.00002750"
                                  :storage_traffic "0.02000000"}
          expected "₣6.57272"]
      (is (= expected (sut selection-list
                           currency-type
                           compute-pricing
                           license-win-price
                           object-storage-pricing))))))

(deftest calculate-total-multiple-selections-assessment-example-test
  (testing "Multiple items in selection list."
    (let [sut sut/calculate-total
          selection-list [{:product-group "Standard Instances"
                           :instance-type "MEDIUM"
                           :local-storage-size 100
                           :windows-license? "No"}
                          {:product-group "Standard Instances"
                           :instance-type "MEDIUM"
                           :local-storage-size 200
                           :windows-license? "Yes"}
                          {:product-group "Standard Instances"
                           :instance-type "MEDIUM"
                           :local-storage-size 235
                           :windows-license? "Yes"}
                          {:product-group "GPU Instances"
                           :instance-type "HUGE"
                           :local-storage-size 1600
                           :windows-license? "No"
                           :gpu-type :gpu2}
                          {:product-group "Additional Features"
                           :snapshot-sizes [100 200 200 235 1600]
                           :eip-address "Yes"
                           :eip-address-amount 3
                           :custom-template "Yes"
                           :custom-template-zones 1
                           :custom-template-size 10
                           :traffic ""
                           :traffic-free ""
                           :dns-package "Small"
                           :object-storage "Yes"
                           :object-storage-size 2000}]
          currency-type "CHF"
          compute-pricing {:running_medium "0.04444000"
                           :running_gpu2_huge "3.31944000"
                           :volume "0.00014000"
                           :volume_data "0.00006000"
                           :eip_address "0.01389000"
                           :snapshot "0.00014000"
                           :template "0.00139000"}
          license-win-price "0.01111000"
          object-storage-pricing {:storage_volume "0.00002750"
                                  :storage_traffic "0.02000000"}
          expected "₣5.21135"]
      (is (= expected (sut selection-list
                           currency-type
                           compute-pricing
                           license-win-price
                           object-storage-pricing))))))

