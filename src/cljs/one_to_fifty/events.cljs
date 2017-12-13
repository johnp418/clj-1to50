(ns one-to-fifty.events
  (:require [re-frame.core :as re-frame]
            [one-to-fifty.db :as db]
            [one-to-fifty.game :refer [new-board]]))



(re-frame/reg-event-db
  ::initialize-db
  (fn  [_ _]
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
  ::click-tile
  (fn [db [_ tile-number]]
    (let [{:keys [last-tile]} db])
    (assoc db :tile tile-number)))

(re-frame/reg-event-db
  ::new-board
  (fn [db [_ _]]
    (assoc db :board (new-board))))

