(ns one-to-fifty.events
  (:require [re-frame.core :as re-frame]
            [one-to-fifty.db :as db]
            [one-to-fifty.game :refer [generate-board initial-state]]))



(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
    (assoc db/default-db :board (generate-board))))

;(re-frame/reg-event-fx
;  ::initialize-db
;  (fn [{:keys [db]} _]
;    {:db ()}))

(re-frame/reg-event-db
  ::set-active-panel
  (fn [db [_ active-panel]]
    (assoc db :active-panel active-panel)))


(re-frame/reg-event-fx
  :click-tile
  (fn [{:keys [db]} [_ idx value]]
    (let [{:keys [next-tile win-tile board]} db
          {:keys [cols data]} board
          next-tile* (inc next-tile)]
      ;; If tile clicked is next tile, change next tile and update board. Dispatch win if value = win-tile
      (cond
        (= value win-tile) {:dispatch [:win]}
        (= value next-tile) { :db (-> db
                                      (assoc :next-tile next-tile*)
                                      (assoc-in [:board :data idx] next-tile*))}
        :else (do
                (println " Not an anwswer tile clicked " value)
                {})))))


(re-frame/reg-event-db
  :win
  (fn [db _]
    (merge db initial-state {:board (generate-board) :score (inc (get-in db [:score]))})))



(re-frame/reg-event-db
  :start-game
  (fn [db _]
    (assoc-in db [:started] true)))

;(re-frame/reg-event-db
;  ::timer)

