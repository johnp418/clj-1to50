(ns user
  (:require
    [mount.core :as mount]
    [one-to-fifty.figwheel :refer [start-fw stop-fw cljs]]
    one-to-fifty.core
    [clojure.tools.logging :as log]))

(defn start []
  (log/info "Started app..")
  (mount/start-without #'one-to-fifty.core/repl-server))

(defn stop []
  (mount/stop-except #'one-to-fifty.core/repl-server))

(defn restart-fw []
  (stop-fw)
  (start-fw))



