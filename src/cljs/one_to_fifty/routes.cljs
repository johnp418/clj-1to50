(ns one-to-fifty.routes
  (:require [secretary.core :as secretary]
            [bidi.bidi :as bidi]
            [pushy.core :as pushy]
            [one-to-fifty.main.events :as events]
            [re-frame.core :as re-frame]))

;;; -------------------------
;;; Routes
(def routes ["/" {""      :home
                  "about" :about}])

(defn- dispatch-route [matched-route]
  (let [panel-name (-> (:handler matched-route)
                       (name)
                       (str "-panel")
                       (keyword))]
    (js/console.log "Panel - name " panel-name)
    (re-frame/dispatch [::events/set-active-panel panel-name])))

(defn- parse-url [url]
  (do (js/console.log " Parsing URL " url)
      (bidi/match-route routes url)))

(def app-routes (bidi/route-seq routes))

(def url-for (partial bidi/path-for routes))

(def history (pushy/pushy dispatch-route parse-url))

(pushy/start! history)
