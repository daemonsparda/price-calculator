(ns price-calculator.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [price-calculator.core-test]))

(doo-tests 'price-calculator.core-test)
