(ns price-calculator.events-test
  (:require [price-calculator.events :as sut]
            [price-calculator.db :as db]
            [cljs.test :refer-macros [deftest is testing run-tests]]))

(deftest initialize-db-test
  (testing "Ensure db is properly initialized with default-db."
    (let [db db/default-db
          event [::initialize-db]
          sut sut/initialize-db]
      (is (= db (sut db event))))))

(deftest set-active-panel-test
  (testing "Ensure db properly updates active-panel."
    (let [db db/default-db
          event [::set-active-panel :lets-get-started]
          sut sut/set-active-panel]
      (is (= :lets-get-started
             (:active-panel (sut db event)))))))

(deftest fetch-license-pricing-success-test
  (testing "Ensure license-pricing properly populate the db."
    (let [db db/default-db
          event [::fetch-license-pricing-success "data"]
          sut sut/fetch-license-pricing-success]
      (is (= "data"
             (:license-pricing-result (:success (:http (sut db event)))))))))

(deftest fetch-license-pricing-failure-test
  (testing "Ensure http failure is caught."
    (let [db db/default-db
          event [::fetch-license-pricing-failure "data"]
          sut sut/fetch-license-pricing-failure]
      (is (= "data"
             (:license-pricing-error (:failure (:http (sut db event)))))))))

(deftest fetch-compute-pricing-success-test
  (testing "Ensure compute-pricing properly populate the db."
    (let [db db/default-db
          event [::fetch-compute-pricing-success "data"]
          sut sut/fetch-compute-pricing-success]
      (is (= "data"
             (:compute-pricing-result (:success (:http (sut db event)))))))))

(deftest fetch-compute-pricing-failure-test
  (testing "Ensure http failure is caught."
    (let [db db/default-db
          event [::fetch-compute-pricing-failure "data"]
          sut sut/fetch-compute-pricing-failure]
      (is (= "data"
             (:compute-pricing-error (:failure (:http (sut db event)))))))))

(deftest fetch-object-storage-pricing-success-test
  (testing "Ensure object-storage-pricing properly populate the db."
    (let [db db/default-db
          event [::fetch-object-storage-pricing-success "data"]
          sut sut/fetch-object-storage-pricing-success]
      (is (= "data"
             (-> db
                 (sut event)
                 :http
                 :success
                 :object-storage-pricing-result))))))

(deftest fetch-object-storage-pricing-failure-test
  (testing "Ensure http failure is caught."
    (let [db db/default-db
          event [::fetch-object-storage-pricing-failure "data"]
          sut sut/fetch-object-storage-pricing-failure]
      (is (= "data"
             (-> db
                 (sut event)
                 :http
                 :failure
                 :object-storage-pricing-error))))))

(deftest set-product-group-test
  (testing "Ensure product-group is properly attached to db."
    (let [db db/default-db
          event [::set-product-group "data"]
          sut sut/set-product-group]
      (is (= "data"
             (:product-group (:current-selection (:db (sut db event)))))))))

(deftest set-gpu-type-test
  (testing "Ensure gpu-type is properly attached to db."
    (let [db db/default-db
          event [::set-gpu-type "data"]
          sut sut/set-gpu-type]
      (is (= "data"
             (:gpu-type (:current-selection (sut db event))))))))

(deftest set-instance-type-test
  (testing "Ensure instance-type is properly attached to db."
    (let [db db/default-db
          event [::set-instance-type "data"]
          sut sut/set-instance-type]
      (is (= "data"
             (:instance-type (:current-selection (sut db event))))))))

(deftest set-local-storage-size-test
  (testing "Ensure local-storage-size is properly attached to db."
    (let [db db/default-db
          event [::set-local-storage-size "data"]
          sut sut/set-local-storage-size]
      (is (= "data"
             (:local-storage-size (:current-selection (sut db event))))))))

(deftest set-license-test
  (testing "Ensure license is properly attached to db."
    (let [db db/default-db
          event [::set-license "data"]
          sut sut/set-license]
      (is (= "data"
             (:windows-license? (:current-selection (sut db event))))))))

(deftest set-currency-type-test
  (testing "Ensure currency-type is properly attached to db."
    (let [db db/default-db
          event [::set-currency-type "data"]
          sut sut/set-currency-type]
      (is (= "data"
             (:currency-type (sut db event)))))))

(deftest add-selection-test
  (testing "Ensure selection is properly added to selection-list."
    (let [db db/default-db
          event [::add-selection "data"]
          sut sut/add-selection]
      (is (= ["data"]
             (:selection-list (sut db event))))
      (is (= ["data" "data"]
             (:selection-list (sut (sut db event) event))))
      (is (= ["data" "data" "data"]
             (:selection-list (sut (sut (sut db event) event) event)))))))

(deftest add-features-test
  (testing "Ensure that additional features are properly stored in db."
    (let [db db/default-db
          event [::add-features "data"]
          sut sut/add-features]
      (is (= "data"
             (:additional-features (sut db event)))))))

(deftest set-dns-package-test
  (testing "Ensure that dns package is properly stored in db."
    (let [db db/default-db
          event [::set-dns-package "data"]
          sut sut/set-dns-package]
      (is (= "data"
             (:dns-package (:current-selection (sut db event))))))))

(deftest set-eip-address-test
  (testing "Ensure that elastic IP address is properly stored in db."
    (let [db db/default-db
          event [::set-eip-address "data"]
          sut sut/set-eip-address]
      (is (= "data"
             (:eip-address (:current-selection (sut db event))))))))

(deftest set-eip-address-amount-test
  (testing "Ensure that elastic IP address amount is properly stored in db."
    (let [db db/default-db
          event [::set-eip-address-amount "data"]
          sut sut/set-eip-address-amount]
      (is (= "data"
             (:eip-address-amount (:current-selection (sut db event))))))))

(deftest set-custom-template-test
  (testing "Ensure that custom template is properly stored in db."
    (let [db db/default-db
          event [::set-custom-template "data"]
          sut sut/set-custom-template]
      (is (= "data"
             (:custom-template (:current-selection (sut db event))))))))

(deftest set-custom-template-size-test
  (testing "Ensure that custom template size is properly stored in db."
    (let [db db/default-db
          event [::set-custom-template-size "data"]
          sut sut/set-custom-template-size]
      (is (= "data"
             (:custom-template-size (:current-selection (sut db event))))))))

(deftest set-custom-template-zones-test
  (testing "Ensure that custom template zones is properly stored in db."
    (let [db db/default-db
          event [::set-custom-template-zones "data"]
          sut sut/set-custom-template-zones]
      (is (= "data"
             (:custom-template-zones (:current-selection (sut db event))))))))

(deftest set-object-storage-test
  (testing "Ensure that object storage is properly stored in db."
    (let [db db/default-db
          event [::set-object-storage "data"]
          sut sut/set-object-storage]
      (is (= "data"
             (:object-storage (:current-selection (sut db event))))))))

(deftest set-object-storage-size-test
  (testing "Ensure that object storage size is properly stored in db."
    (let [db db/default-db
          event [::set-object-storage-size "data"]
          sut sut/set-object-storage-size]
      (is (= "data"
             (:object-storage-size (:current-selection (sut db event))))))))
