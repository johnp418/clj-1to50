(ns one-to-fifty.events
  (:require [re-frame.core :as re-frame]
            [one-to-fifty.db :as db]
            [one-to-fifty.game :refer [new-board initial-state]]))



(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
    (assoc db/default-db :board (new-board))))

;(re-frame/reg-event-fx
;  ::initialize-db
;  (fn [{:keys [db]} _]
;    {:db ()}))

(re-frame/reg-event-db
  ::set-active-panel
  (fn [db [_ active-panel]]
    (assoc db :active-panel active-panel)))


(re-frame/reg-event-db
  :click-tile
  (fn [db [_ row col value]]
    (let [{:keys [last-tile win-tile board]} db
          should_add? (not (nil? last-tile))
          last-tile-add (when should_add?
                          (if (< last-tile win-tile)
                            (inc last-tile)
                            nil))]
      (-> db
          (assoc :next-tile (inc value) :last-tile last-tile-add)
          (assoc-in [:board row col :value] last-tile-add)))))


(re-frame/reg-event-db
  :win
  (fn [db _]
    (println "Game won")
    ;; TODO: Better way to reset state?
    (merge db initial-state {:board (new-board) :score (inc (get-in db [:score]))})))

(re-frame/reg-event-db
  ::new-board
  (fn [db [_ _]]
    (assoc db :board (new-board))))

(re-frame/reg-event-db
  :start-game
  (fn [db _]
    (assoc-in db [:started] true)))

;(re-frame/reg-event-db
;  ::timer)

