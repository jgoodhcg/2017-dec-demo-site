(ns app.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [app.core-test]))

(doo-tests 'app.core-test)

