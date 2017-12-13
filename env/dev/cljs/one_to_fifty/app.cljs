(ns ^:figwheel-no-load one-to-fifty.app
  (:require [one-to-fifty.core :as core]
            [devtools.core :as devtools]))

(enable-console-print!)

(devtools/install!)

(core/init!)
