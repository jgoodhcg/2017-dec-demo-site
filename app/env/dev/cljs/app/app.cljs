(ns ^:figwheel-no-load app.app
  (:require [app.core :as core]
            [devtools.core :as devtools]))

(enable-console-print!)

(devtools/install!)

(core/init!)
