(ns one-to-fifty.views
  (:require [re-frame.core :as re-frame]
            [one-to-fifty.main.subs :as subs]
            [one-to-fifty.routes :as routes]
            [reagent.core :as r]
            [bidi.bidi :as bidi]))

(defn tile [idx value]
  [:div.tile-inner
   {:on-click #(re-frame/dispatch [:click-tile idx value])}
   value])

;; Game status
(defn status []
  (let [next-tile @(re-frame/subscribe [::subs/next-tile])
        score @(re-frame/subscribe [::subs/score])]
    [:div.status
     ;[timer]
     [:div.hint
      [:h3 (str "Next tile :" next-tile)]]
     [:div.score
      [:h3 (str "Score:" score)]]]))


(defn board-inner [{:as board :keys [cols data]}]
  [:div.board
   (if board
     (let [width (str (/ 100 cols) "%")]
       (->> data
            (map-indexed
              (fn [idx v]
                ^{:key (str "tile-" idx)}
                ;; Row
                [:div.tile {:style {:width width :height width}}
                 [tile idx v]])
              )))

     ;; Empty board
     "Empty")])

;; Board component
(defn board-section []
  (let [board @(re-frame/subscribe [::subs/board])
        started @(re-frame/subscribe [::subs/started])]
    [:div.game
     #_(js/console.log "Board => " board)
     ;; Game status
     [status]
     (if (not started)

       ;; Start button
       [:div.start
        [:button {:on-click #(re-frame/dispatch [:start-game])} "Start"]]

       ;; Board
       [board-inner board]
       )]))


(defn route-item [name]
  [:li.route
   [:a (if-not name "Main" name)]])

(defn routes-list []
  [:ul.list-items
   (map (fn [r]
          ^{:key (:handler r)} [route-item (-> r
                                               (:path)
                                               (get 1))]) routes/app-routes)])

;; home
(defn home-panel []
  [:div.main
   [:nav
    [routes-list]]
   [board-section]])


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
  (let [active-panel @(re-frame/subscribe [::subs/active-panel])]
    [show-panel active-panel]))
