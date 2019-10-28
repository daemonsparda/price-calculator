(ns price-calculator.components.choose
  (:require [re-frame.core :as rf]
            [price-calculator.events :as events]
            [price-calculator.subs :as subs]))

(defn product-group-information
  []
  (case @(rf/subscribe [::subs/product-group])
       "Standard Instances"
       [:div.card
        [:div.card-body
         [:h6.card-title "Standard Instances"]
         [:p.card-text "Machines with a balanced mix of CPU cores, RAM and SSD local storage, covering a variety of use cases and allowing you to implement your architecture. From simple web hosting up to specialized Database host, from Continuous Integration server up to large Data Lakes, you can scale and combine Standard Instances to fit your application needs."]]]

       "Storage Optimized Instances"
       [:div.card
        [:div.card-body
         [:h6.card-title "Storage Optimized Instances"]
         [:p.card-text "Designed to cover the needs of data-hungry applications requiring to store and access large amounts of data on a reliable local storage, Storage Optimized Instances have the same mix of CPU and RAM than our Standard Instances, but make use of larger and more capable SSD hard drives, greatly expanding the overall data capacity."]]]

       "GPU Instances"
       [:div.card
        [:div.card-body
         [:h6.card-title "GPU Instances"]
         [:p.card-text "GPU virtual machines that provide up to 4 dedicated NVIDIA graphic cards to perform Machine/Deep learning, high performance computing, and all sort of intensive computation more efficiently than on Standard Instances."]]]

       "Additional Features"
       [:div.card
        [:div.card-body
         [:h6.card-title "Additional Features"]
         [:p.card-text "Features and additional services to build up your architecture such as: Bandwidth, Elastic IP, Snapshots, Custom Templates"]]]

       nil))

(defn button
  [text event subscription]
  [:button {:type "button"
            :class (str "btn btn-danger " (if (= text @(rf/subscribe [subscription]))
                                            "active"
                                            nil))
            :on-click #(rf/dispatch [event text])}
   text])

(defn product-group
  []
  (let [product-group-list @(rf/subscribe [::subs/product-group-list])]
    [:div
     [:h5 "Please choose a product you'd like to add to your list."]
     [:div {:class "btn-group"
            :role "group"
            :aria-label "Product Groups"}
      (doall (for [product-group product-group-list]
               ^{:key (str "product-group-" product-group)}
               [button product-group ::events/set-product-group ::subs/product-group]))]
     [product-group-information]]))

(defn instance-type
  [product-group-key]
  (let [instance-type-list @(rf/subscribe [::subs/instance-type-list product-group-key])]
    [:div
     [:h5 "Choose an instance type"]
     [:div {:class "btn-group"
            :role "group"
            :aria-label "Instance Types"}
      (doall (for [instance-type instance-type-list]
               ^{:key (str "instance-type-" instance-type)}
               [button instance-type ::events/set-instance-type ::subs/instance-type]))]]))

(defn gpu-type-information
  []
  (case @(rf/subscribe [::subs/gpu-type])
    "GPU2 (Tesla V100)"
    [:div.card
     [:div.card-body
      [:h6.card-title "GPU2 (Tesla V100)"]
      [:p.card-text "Nearly the double of single precision and double precision teraflops compared to the P100, and 640 dedicated Tensor Cores to train AI models that would consume weeks of computing resources in a few days."]]]

    "GPU (Tesla P100)"
    [:div.card
     [:div.card-body
      [:h6.card-title "GPU (Tesla P100)"]
      [:p.card-text "An affordable solution to enter the HPC world, with no cost of acquisition and a dramatically reduced cost of ownership."]]]

    nil))

(defn gpu-type
  [product-group-key]
  (when (= :gpu-instances product-group-key)
    (let [gpu2 "GPU2 (Tesla V100)"
          gpu "GPU (Tesla P100)"]
      [:div
       [:h5 "There are two types of GPU Instances, which one would you prefer?"]
       [:div {:class "btn-group"
              :role "group"
              :aria-label "Instance Types"}
        [button gpu2 ::events/set-gpu-type ::subs/gpu-type]
        [button gpu ::events/set-gpu-type ::subs/gpu-type]]
       [gpu-type-information]])))

(defn license
  []
  [:div
   [:h5 "Do you require a Windows Instance?"]
   [:div {:class "btn-group"
          :role "group"
          :aria-label "Instance Types"}
    (for [value ["Yes" "No"]]
      ^{:key (str "windows-license-" value)}      
      [button value ::events/set-license ::subs/windows-license?])]])

(defn local-storage-size
  [product-group-key]
  (let [product-group-key @(rf/subscribe [::subs/product-group-key])
        instance-type @(rf/subscribe [::subs/instance-type])
        range @(rf/subscribe [::subs/local-storage-range product-group-key instance-type])
        min (:min range)
        max (:max range)]
    [:div
     [:h5 (str "Choose local storage size for your instance (Between " min " GB and " max " GB):")]
     [:div.container.row
      [:input.form-control.col-sm-2
       {:type "number"
        :name "local-storage-size"
        :min min
        :max max
        :on-change #(rf/dispatch [::events/set-local-storage-size (-> % .-target .-value)])}]]]))

(defn snapshot
  []
  [:div
   [:h5 "Will you need a snapshot of this local storage?"]
   [:div {:class "btn-group"
          :role "group"
          :aria-label "Snapshots"}
    (doall (for [value ["Yes" "No"]]
             ^{:key (str "btn-eip-" value)}
             [button value ::events/set-snapshot ::subs/snapshot]))]
   (when (= "Yes" @(rf/subscribe [::subs/snapshot]))
     [:div.row
      [:div.col-sm-3
       [:p]
       [:h5 "How many?"]
       [:input.form-control
        {:type "number"
         :name "snapshot-amount"
         :on-change #(rf/dispatch [::events/set-snapshot-amount (-> % .-target .-value)])}]]])])

(defn currency-type
  []
  [:div {:style {:position "fixed"
                 :right "15px"
                 :top "15px"}}
   [:div.card
    [:h5.card-header "Choose your currency:"]
    [:ul.list-group.list-group-flush
     (doall (for [value ["CHF" "EUR" "USD"]]
              ^{:key (str "btn-currency-" value)}
              [:li
               {:class "list-group-item text-center"}
               [button value ::events/set-currency-type ::subs/currency-type]]))]]])

(defn dns-package-information
  []
  (let [dns-package (rf/subscribe [::subs/dns-package])
        currency-type (rf/subscribe [::subs/currency-type])
        currency-symbol (case @currency-type
                          "CHF" "₣"
                          "EUR" "€"
                          "USD" "$")]
    (when-not (= "No" @dns-package)
      [:div.container
       (str "DNS Pricing is paid per month so it is not included in the total price. The monthly price for a "
            @dns-package
            " DNS package is "
            currency-symbol
            (if (= "EUR" @currency-type)
              (case @dns-package
                "Small" 1
                "Medium" 5
                "Large" 23)
              (case @dns-package
                      "Small" 1
                      "Medium" 5
                      "Large" 25))
            ".00")])))

(defn dns-package
  []
  [:div
   [:h5 "Do you require a DNS Package?"]
   [:div {:class "btn-group"
          :role "group"
          :aria-label "DNS Package"}
    (doall (for [value ["Small" "Medium" "Large" "No"]]
             ^{:key (str "btn-dns-" value)}
             [button value ::events/set-dns-package ::subs/dns-package]))]
   [:p]
   #_[dns-package-information]])

(defn eip-address
  []
  (let []
    [:div
     [:h5 "Do you require an Elastic IP Address?"]
     [:div {:class "btn-group"
            :role "group"
            :aria-label "Elatic IP Address"}
      (doall (for [value ["Yes" "No"]]
               ^{:key (str "btn-eip-" value)}
               [button value ::events/set-eip-address ::subs/eip-address]))]
     (when (= "Yes" @(rf/subscribe [::subs/eip-address]))
       [:div.row
        [:div.col-sm-3
         [:p]
         [:h5 "How many?"]
         [:input.form-control
          {:type "number"
           :name "eip-address-amount"
           :on-change #(rf/dispatch [::events/set-eip-address-amount (-> % .-target .-value)])}]]])]))

(defn custom-templates
  []
  [:div
   [:h5 "Do you require any custom templates?"]
   [:div {:class "btn-group"
          :role "group"
          :aria-label "DNS Package"}]
   (doall (for [value ["Yes" "No"]]
            ^{:key (str "btn-eip-" value)}
            [button value ::events/set-custom-template ::subs/custom-template]))
   (when (= "Yes" @(rf/subscribe [::subs/custom-template]))
     [:div.row
      [:div.col-sm-3
       [:p]
       [:h5 "What size? (in GB)"]
       [:input.form-control
        {:type "number"
         :name "custom-template-size"
         :min 0
         :on-change #(rf/dispatch [::events/set-custom-template-size (-> % .-target .-value)])}]]
      [:div.col-sm-3
       [:p]
       [:h5 "In how many zones?"]
       [:input.form-control
        {:type "number"
         :name "custom-template-zones"
         :min 0
         :on-change #(rf/dispatch [::events/set-custom-template-zones (-> % .-target .-value)])}]]])])

(defn object-storage
  []
  [:div
   [:h5 "Do you require object storage(S3)"]
   [:div {:class "btn-group"
          :role "group"
          :aria-label "Object Storage"}]
   (doall (for [value ["Yes" "No"]]
            ^{:key (str "btn-eip-" value)}
            [button value ::events/set-object-storage ::subs/object-storage]))
   (when (= "Yes" @(rf/subscribe [::subs/object-storage]))
     [:div.row
      [:div.col-sm-3
       [:p]
       [:h5 "What size? (in GB)"]
       [:input.form-control
        {:type "number"
         :name "object-storage-size"
         :min 0
         :on-change #(rf/dispatch [::events/set-object-storage-size (-> % .-target .-value)])}]]])])

