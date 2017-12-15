(ns one-to-fifty.views
  (:require [re-frame.core :as re-frame]
            [one-to-fifty.subs :as subs]
            [one-to-fifty.game :refer [win-tile]]
            [reagent.core :as r]))

(defn tile [{:keys [row col value]}]
  (let [next-tile @(re-frame/subscribe [::subs/next-tile])]
    [:div.tile {:on-click
                (if (= next-tile value)
                  (fn [e]
                    (js/console.log "Clicked " value " Next " next-tile)
                    (if (= win-tile value)
                      (re-frame/dispatch [:win])
                      (re-frame/dispatch [:click-tile row col value])))
                  (fn [e]
                    (js/console.log "Not an answer, next is " next-tile)))
                } value]))

(defn row [items]
  [:div.board-row
   (for [{:keys [row col value] :as v} items]
     ^{:key (str "row-" row "-col-" col)} [tile v])])

;; Game status
(defn status []
  (let [next-tile @(re-frame/subscribe [::subs/next-tile])
        score @(re-frame/subscribe [::subs/score])]
    [:div.status
     ;[timer]
     [:div.hint
      [:h3 (str "Next tile:" next-tile)]]
     [:div.score
      [:h3 (str "Score:" score)]]]))


;; Board component
(defn board []
  (let [board @(re-frame/subscribe [::subs/board])
        started @(re-frame/subscribe [::subs/started])]
    [:div.game #_{:on-click (fn []
                              (re-frame/dispatch [:test "random value"]))}
     [status]
     (if (not started)
       [:div.start
        [:button {:on-click #(re-frame/dispatch [:start-game])} "Start"]]
       [:div.board
        (if board
          (map-indexed (fn [idx v]
                         ^{:key (str "row-" idx)} [row v]) board)
          "Empty")])

     #_(if board
         (do
           (println board)
           (for [{:keys [row col value] :as t} board]
             ^{:key (str "row-" row "-col-" col)} [tile t]))
         "Empty")]))

;; home
(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [board]))


;; about
(defn about-panel []
  [:div "This is the About Page."
   [:div [:a {:href "#/"} "go to Home Page"]]])


;; main
(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
