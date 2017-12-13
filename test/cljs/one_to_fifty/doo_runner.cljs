(ns one-to-fifty.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [one-to-fifty.core-test]))

(doo-tests 'one-to-fifty.core-test)

