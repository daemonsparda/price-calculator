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

(deftest instance-type-list-std-instances-test
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
             (sut db sub))))))

(deftest instance-type-list-storage-instances-test
  (testing "Ensure instance-type-list properly returns a list of instance type for storage optimized instances."
    (let [db db/default-db
          sub [::instance-type-list :storage-optimized-instances]
          sut sut/instance-type-list]
      (is (= ["EXTRA-LARGE"
              "HUGE"
              "MEGA"
              "TITAN"
              "JUMBO"]
             (sut db sub))))))

(deftest instance-type-list-gpu-instances-test
  (testing "Ensure instance-type-list properly returns a list of instance type for GPU instances."
    (let [db db/default-db
          sub [::instance-type-list :gpu-instances]
          sut sut/instance-type-list]
      (is (= ["SMALL"
              "MEDIUM"
              "LARGE"
              "HUGE"]
             (sut db sub))))))

(deftest local-storage-range-std-micro-test
  (testing "Standard Instance - Type: MICRO"
    (let [db db/default-db
          sub [::local-storage-range :standard-instances "MICRO"]
          sut sut/local-storage-range]
      (is (= {:min 10
              :max 200}
             (sut db sub))))))

(deftest local-storage-range-std-tiny-test
  (testing "Standard Instance - Type: TINY"
    (let [db db/default-db
          sub [::local-storage-range :standard-instances "TINY"]
          sut sut/local-storage-range]
      (is (= {:min 10
              :max 400}
             (sut db sub))))))

(deftest local-storage-range-std-small-test
  (testing "Standard Instance - Type: SMALL"
    (let [db db/default-db
          sub [::local-storage-range :standard-instances "SMALL"]
          sut sut/local-storage-range]
      (is (= {:min 10
              :max 400}
             (sut db sub))))))

(deftest local-storage-range-std-medium-test
  (testing "Standard Instance - Type: MEDIUM"
    (let [db db/default-db
          sub [::local-storage-range :standard-instances "MEDIUM"]
          sut sut/local-storage-range]
      (is (= {:min 10
              :max 400}
             (sut db sub))))))

(deftest local-storage-range-std-large-test
  (testing "Standard Instance - Type: LARGE"
    (let [db db/default-db
          sub [::local-storage-range :standard-instances "LARGE"]
          sut sut/local-storage-range]
      (is (= {:min 10
              :max 400}
             (sut db sub))))))

(deftest local-storage-range-std-extra-large-test
  (testing "Standard Instance - Type: EXTRA-LARGE"
    (let [db db/default-db
          sub [::local-storage-range :standard-instances "EXTRA-LARGE"]
          sut sut/local-storage-range]
      (is (= {:min 10
              :max 800}
             (sut db sub))))))

(deftest local-storage-range-std-huge-test
  (testing "Standard Instance - Type: HUGE"
    (let [db db/default-db
          sub [::local-storage-range :standard-instances "HUGE"]
          sut sut/local-storage-range]
      (is (= {:min 10
              :max 800}
             (sut db sub))))))

(deftest local-storage-range-std-mega-test
  (testing "Standard Instance - Type: MEGA"
    (let [db db/default-db
          sub [::local-storage-range :standard-instances "MEGA"]
          sut sut/local-storage-range]
      (is (= {:min 10
              :max 800}
             (sut db sub))))))

(deftest local-storage-range-std-titan-test
  (testing "Standard Instance - Type: TITAN"
    (let [db db/default-db
          sub [::local-storage-range :standard-instances "TITAN"]
          sut sut/local-storage-range]
      (is (= {:min 10
              :max 1600}
             (sut db sub))))))

(deftest local-storage-range-std-jumbo-test
  (testing "Standard Instance - Type: JUMBO"
    (let [db db/default-db
          sub [::local-storage-range :standard-instances "JUMBO"]
          sut sut/local-storage-range]
      (is (= {:min 10
              :max 1600}
             (sut db sub))))))

(deftest local-storage-range-storage-extra-large-test
  (testing "Storage Optimized Instance - Type: EXTRA-LARGE"
    (let [db db/default-db
          sub [::local-storage-range :storage-optimized-instances "EXTRA-LARGE"]
          sut sut/local-storage-range]
      (is (= {:min 1000
              :max 2000}
             (sut db sub))))))

(deftest local-storage-range-storage-huge-test
  (testing "Storage Optimized Instance - Type: HUGE"
    (let [db db/default-db
          sub [::local-storage-range :storage-optimized-instances "HUGE"]
          sut sut/local-storage-range]
      (is (= {:min 2000
              :max 3000}
             (sut db sub))))))

(deftest local-storage-range-storage-mega-test
  (testing "Storage Optimized Instance - Type: MEGA"
    (let [db db/default-db
          sub [::local-storage-range :storage-optimized-instances "MEGA"]
          sut sut/local-storage-range]
      (is (= {:min 3000
              :max 5000}
             (sut db sub))))))

(deftest local-storage-range-storage-titan-test
  (testing "Storage Optimized Instance - Type: TITAN"
    (let [db db/default-db
          sub [::local-storage-range :storage-optimized-instances "TITAN"]
          sut sut/local-storage-range]
      (is (= {:min 5000
              :max 10000}
             (sut db sub))))))

(deftest local-storage-range-storage-jumbo-test
  (testing "Storage Optimized Instance - Type: JUMBO"
    (let [db db/default-db
          sub [::local-storage-range :storage-optimized-instances "JUMBO"]
          sut sut/local-storage-range]
      (is (= {:min 10000
              :max 20000}
             (sut db sub))))))

(deftest local-storage-range-gpu-small-test
  (testing "GPU Instance - Type: SMALL"
    (let [db db/default-db
          sub [::local-storage-range :gpu-instances "SMALL"]
          sut sut/local-storage-range]
      (is (= {:min 100
              :max 800}
             (sut db sub))))))

(deftest local-storage-range-gpu-medium-test
  (testing "GPU Instance - Type: MEDIUM"
    (let [db db/default-db
          sub [::local-storage-range :gpu-instances "MEDIUM"]
          sut sut/local-storage-range]
      (is (= {:min 100
              :max 1200}
             (sut db sub))))))

(deftest local-storage-range-gpu-large-test
  (testing "GPU Instance - Type: LARGE"
    (let [db db/default-db
          sub [::local-storage-range :gpu-instances "LARGE"]
          sut sut/local-storage-range]
      (is (= {:min 100
              :max 1600}
             (sut db sub))))))

(deftest local-storage-range-gpu-huge-test
  (testing "GPU Instance - Type: HUGE"
    (let [db db/default-db
          sub [::local-storage-range :gpu-instances "HUGE"]
          sut sut/local-storage-range]
      (is (= {:min 100
              :max 1600}
             (sut db sub))))))


