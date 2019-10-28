(ns price-calculator.db)

(def default-db
  {:name "Price Calculator"
   :current-selection {:product-group "Standard Instances"}
   :selection-list []
   :currency-type "CHF"
   :product-groups {:standard-instances
                    {:name "Standard Instances"
                     :instance-types
                     [{:name "MICRO"
                       :ram "512 MB"
                       :cpu-cores "1 Core"
                       :local-storage {:min 10
                                       :max 200}}
                      {:name "TINY"
                       :ram "1 GB"
                       :cpu-cores "1 Core"
                       :local-storage {:min 10
                                       :max 400}}
                      {:name "SMALL"
                       :ram "2 GB"
                       :cpu-cores "2 Cores"
                       :local-storage {:min 10
                                       :max 400}}
                      {:name "MEDIUM"
                       :ram "4 GB"
                       :cpu-cores "2 Cores"
                       :local-storage {:min 10
                                       :max 400}}
                      {:name "LARGE"
                       :ram "8 GB"
                       :cpu-cores "4 Cores"
                       :local-storage {:min 10
                                       :max 400}}
                      {:name "EXTRA-LARGE"
                       :ram "16 GB"
                       :cpu-cores "4 Cores"
                       :local-storage {:min 10
                                       :max 800}}
                      {:name "HUGE"
                       :ram "32 GB"
                       :cpu-cores "8 Core"
                       :local-storage {:min 10
                                       :max 800}}
                      {:name "MEGA"
                       :ram "64 GB"
                       :cpu-cores "12 Cores"
                       :local-storage {:min 10
                                       :max 800}}
                      {:name "TITAN"
                       :ram "128 GB"
                       :cpu-cores "16 Cores"
                       :local-storage {:min 10
                                       :max 1600}}
                      {:name "JUMBO"
                       :ram "225 GB" ;; 256 GB??
                       :cpu-cores "24 Core"
                       :local-storage {:min 10
                                       :max 1600}}]}
                    :storage-optimized-instances
                    {:name "Storage Optimized Instances"
                     :instance-types [{:name "EXTRA-LARGE"
                                       :ram "16 GB"
                                       :cpu-cores "4 Cores"
                                       :local-storage {:min 1000
                                                       :max 2000}}
                                      {:name "HUGE"
                                       :ram "32 GB"
                                       :cpu-cores "8 Core"
                                       :local-storage {:min 2000
                                                       :max 3000}}
                                      {:name "MEGA"
                                       :ram "64 GB"
                                       :cpu-cores "12 Cores"
                                       :local-storage {:min 3000
                                                       :max 5000}}
                                      {:name "TITAN"
                                       :ram "128 GB"
                                       :cpu-cores "16 Cores"
                                       :local-storage {:min 5000
                                                       :max 10000}}
                                      {:name "JUMBO"
                                       :ram "225 GB" ;; 256 GB??
                                       :cpu-cores "24 Core"
                                       :local-storage {:min 10000
                                                       :max 20000}}]}
                    :gpu-instances {:name "GPU Instances"
                                    :gpu2 {:name "GPU2 (Tesla V100)"
                                           :instance-types
                                           [{:name "SMALL"
                                             :ram "56 GB"
                                             :cpu-cores "12 Cores"
                                             :gpu-cards "1 GPU"
                                             :local-storage {:min 100
                                                             :max 800}}
                                            {:name "MEDIUM"
                                             :ram "90 GB"
                                             :cpu-cores "16 Cores"
                                             :gpu-cards "2 GPU"
                                             :local-storage {:min 100
                                                             :max 1200}}
                                            {:name "LARGE"
                                             :ram "120 GB"
                                             :cpu-cores "24 Cores"
                                             :gpu-cards "3 GPU"
                                             :local-storage {:min 100
                                                             :max 1600}}
                                            {:name "HUGE"
                                             :ram "225 GB"
                                             :cpu-cores "48 Cores"
                                             :gpu-cards "4 GPU"
                                             :local-storage {:min 100
                                                             :max 1600}}]}
                                    :gpu {:name "GPU (Tesla P100)"
                                          :instance-types
                                          [{:name "SMALL"
                                            :ram "56 GB"
                                            :cpu-cores "12 Cores"
                                            :gpu-cards "1 GPU"
                                            :local-storage {:min 100
                                                            :max 800}}
                                           {:name "MEDIUM"
                                            :ram "90 GB"
                                            :cpu-cores "16 Cores"
                                            :gpu-cards "2 GPU"
                                            :local-storage {:min 100
                                                            :max 1200}}
                                           {:name "LARGE"
                                            :ram "120 GB"
                                            :cpu-cores "24 Cores"
                                            :gpu-cards "3 GPU"
                                            :local-storage {:min 100
                                                            :max 1600}}
                                           {:name "HUGE"
                                            :ram "225 GB"
                                            :cpu-cores "48 Cores"
                                            :gpu-cards "4 GPU"
                                            :local-storage {:min 100
                                                            :max 1600}}]}}
                    :additional-features
                    {:name "Additional Features"
                     :features [{:name "Bandwidth"}
                                {:name "Elastic IP"}
                                {:name "Snapshots"}
                                {:name "Custom Templates"}
                                {:name "Object Storage"}
                                [:name "DNS"]]}}})
