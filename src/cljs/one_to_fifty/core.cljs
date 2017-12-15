(ns one-to-fifty.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [secretary.core :as secretary]
            [goog.events :as gevents]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [ajax.core :refer [GET POST]]
            [one-to-fifty.ajax :refer [load-interceptors!]]
            [one-to-fifty.events :as events]
            [one-to-fifty.views :refer [main-panel]]
            [re-frame.core :as re-frame])
  (:import goog.History))

;; Page
(defn page []
  (main-panel))


;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (gevents/listen
      HistoryEventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app

(defn mount-components []
  (rf/clear-subscription-cache!)
  (r/render [#'page] (.getElementById js/document "app")))

(defn app-routes []
  (secretary/set-config! :prefix "#")
  ;; --------------------
  ;; define routes here
  (secretary/defroute "/" []
            (re-frame/dispatch [::events/set-active-panel :home-panel]))

  (secretary/defroute "/about" []
            (re-frame/dispatch [::events/set-active-panel :about-panel]))

  ;; --------------------
  (hook-browser-navigation!))


(defn init! []
  (app-routes)
  (rf/dispatch-sync [::events/initialize-db])
  (load-interceptors!)
  (hook-browser-navigation!)
  (mount-components))
