(ns one-to-fifty.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [one-to-fifty.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[one-to-fifty started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[one-to-fifty has shut down successfully]=-"))
   :middleware wrap-dev})
