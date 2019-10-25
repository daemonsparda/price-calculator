(ns price-calculator.subs-test
  (:require [price-calculator.subs :as sut]
            [price-calculator.db :as db]
            [cljs.test :refer-macros [deftest is testing run-tests]]))

(deftest product-group-list-test
  (testing "Ensure product-group-list properly returns a list of product groups."
    (let [db db/default-db
          sut sut/product-group-list]
      (is (= ["Standard Instances"
              "Storage Optimized Instances"
              "GPU Instances"
              "Additional Features"]
             (sut db))))))

(deftest instance-type-list-test
  (testing "Ensure instance-type-list properly returns a list of instance type for standard instances."
    (let [db db/default-db
          sub [::instance-type-list :standard-instances]
          sut sut/instance-type-list]
      (is (= ["MICRO"
              "TINY"
              "SMALL"
              "MEDIUM"
              "LARGE"
              "EXTRA-LARGE"
              "HUGE"
              "MEGA"
              "TITAN"
              "JUMBO"]
             (sut db sub)))))
  (testing "Ensure instance-type-list properly returns a list of instance type for storage optimized instances."
    (let [db db/default-db
          sub [::instance-type-list :storage-optimized-instances]
          sut sut/instance-type-list]
      (is (= ["EXTRA-LARGE"
              "HUGE"
              "MEGA"
              "TITAN"
              "JUMBO"]
             (sut db sub)))))
  (testing "Ensure instance-type-list properly returns a list of instance type for GPU instances."
    (let [db db/default-db
          sub [::instance-type-list :gpu-instances]
          sut sut/instance-type-list]
      (is (= ["SMALL"
              "MEDIUM"
              "LARGE"
              "HUGE"]
             (sut db sub))))))

(deftest local-storage-range-test
  (testing "Ensure local-storage-range is properly set for standard instances."
    (testing "Instance type: MICRO"
      (let [db db/default-db
            sub [::local-storage-range :standard-instances "MICRO"]
            sut sut/local-storage-range]
        (is (= {:min 10
                :max 200}
               (sut db sub)))))
    (testing "Instance type: TINY"
      (let [db db/default-db
            sub [::local-storage-range :standard-instances "TINY"]
            sut sut/local-storage-range]
        (is (= {:min 10
                :max 400}
               (sut db sub)))))
    (testing "Instance type: SMALL"
      (let [db db/default-db
            sub [::local-storage-range :standard-instances "SMALL"]
            sut sut/local-storage-range]
        (is (= {:min 10
                :max 400}
               (sut db sub)))))
    (testing "Instance type: MEDIUM"
        (let [db db/default-db
              sub [::local-storage-range :standard-instances "MEDIUM"]
              sut sut/local-storage-range]
          (is (= {:min 10
                  :max 400}
                 (sut db sub)))))
    (testing "Instance type: LARGE"
      (let [db db/default-db
            sub [::local-storage-range :standard-instances "LARGE"]
            sut sut/local-storage-range]
        (is (= {:min 10
                :max 400}
               (sut db sub)))))
    (testing "Instance type: EXTRA-LARGE"
      (let [db db/default-db
            sub [::local-storage-range :standard-instances "EXTRA-LARGE"]
            sut sut/local-storage-range]
        (is (= {:min 10
                :max 800}
               (sut db sub)))))
    (testing "Instance type: HUGE"
      (let [db db/default-db
            sub [::local-storage-range :standard-instances "HUGE"]
            sut sut/local-storage-range]
        (is (= {:min 10
                :max 800}
               (sut db sub)))))
    (testing "Instance type: MEGA"
      (let [db db/default-db
            sub [::local-storage-range :standard-instances "MEGA"]
            sut sut/local-storage-range]
        (is (= {:min 10
                :max 800}
               (sut db sub)))))
    (testing "Instance type: TITAN"
      (let [db db/default-db
            sub [::local-storage-range :standard-instances "TITAN"]
            sut sut/local-storage-range]
        (is (= {:min 10
                :max 1600}
               (sut db sub)))))
    (testing "Instance type: JUMBO"
      (let [db db/default-db
            sub [::local-storage-range :standard-instances "JUMBO"]
            sut sut/local-storage-range]
        (is (= {:min 10
                :max 1600}
               (sut db sub))))))
  (testing "Ensure local-storage-range is properly set for storage optimized instances."
    (testing "Instance type: EXTRA-LARGE"
      (let [db db/default-db
            sub [::local-storage-range :storage-optimized-instances "EXTRA-LARGE"]
            sut sut/local-storage-range]
        (is (= {:min 1000
                :max 2000}
               (sut db sub)))))
    (testing "Instance type: HUGE"
      (let [db db/default-db
            sub [::local-storage-range :storage-optimized-instances "HUGE"]
            sut sut/local-storage-range]
        (is (= {:min 2000
                :max 3000}
               (sut db sub)))))
    (testing "Instance type: MEGA"
      (let [db db/default-db
            sub [::local-storage-range :storage-optimized-instances "MEGA"]
            sut sut/local-storage-range]
        (is (= {:min 3000
                :max 5000}
               (sut db sub)))))
    (testing "Instance type: TITAN"
      (let [db db/default-db
            sub [::local-storage-range :storage-optimized-instances "TITAN"]
            sut sut/local-storage-range]
        (is (= {:min 5000
                :max 10000}
               (sut db sub)))))
    (testing "Instance type: JUMBO"
      (let [db db/default-db
            sub [::local-storage-range :storage-optimized-instances "JUMBO"]
            sut sut/local-storage-range]
        (is (= {:min 10000
                :max 20000}
               (sut db sub))))))
  (testing "Ensure local-storage-range is properly set for gpu instances."
    (testing "Instance type: SMALL"
      (let [db db/default-db
            sub [::local-storage-range :gpu-instances "SMALL"]
            sut sut/local-storage-range]
        (is (= {:min 100
                :max 800}
               (sut db sub)))))
    (testing "Instance type: MEDIUM"
      (let [db db/default-db
            sub [::local-storage-range :gpu-instances "MEDIUM"]
            sut sut/local-storage-range]
        (is (= {:min 100
                :max 1200}
               (sut db sub)))))
    (testing "Instance type: LARGE"
      (let [db db/default-db
            sub [::local-storage-range :gpu-instances "LARGE"]
            sut sut/local-storage-range]
        (is (= {:min 100
                :max 1600}
               (sut db sub)))))
    (testing "Instance type: HUGE"
      (let [db db/default-db
            sub [::local-storage-range :gpu-instances "HUGE"]
            sut sut/local-storage-range]
        (is (= {:min 100
                :max 1600}
               (sut db sub)))))))


