(ns one-to-fifty.views
  (:require [re-frame.core :as re-frame]
            [one-to-fifty.subs :as subs]
            ))

(defn tile [number]
  (let [match-tile @(re-frame/subscribe [::subs/match-tile])]
    [:div.tile {:on-click (if (= number match-tile)
                            (re-frame/dispatch [::click-tile number]))} number]))

(defn row [items]
  [:div.row
   (for [col items]
     ^{:key col} [tile col])])


(defn board []
  (let [board-rows @(re-frame/subscribe [::subs/board-rows])]
    (println board-rows)
    [:div.board
     (when board-rows
       (map-indexed (fn [idx v]
                      ^{:key (str "row-" idx)} [row v]) board-rows))]))

;; home

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div.board
     [board]]))


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
