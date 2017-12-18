(ns one-to-fifty.main.events
  (:require [re-frame.core :as re-frame]
            [cljs.spec.alpha :as s]

            [one-to-fifty.db :as db]
            [one-to-fifty.main.core :refer [generate-board]]))


(def db-spec ::db/db)

(defn check-and-throw [spec db]
  (when-not (s/valid? spec db)
    (throw (ex-info (str "spec check failed: " (s/explain-str spec db)) {}))))

(def check-spec (re-frame/after (partial check-and-throw db-spec)))



;; TODO: Write test for this event handler?
(defn click-tile [{db :db} [_ idx value]]
  (let [{:keys [next-tile win-tile last-tile board]} db
        {:keys [cols data]} board
        replace-tile (if (< last-tile win-tile)
                       (inc last-tile)
                       nil)]
    ;; If tile clicked is next tile, change next tile and update board. Dispatch win if value = win-tile
    (cond
      (= value win-tile next-tile) {:dispatch [:win]}
      (= value next-tile) {:db (-> db
                                   (assoc :next-tile (inc next-tile)
                                          :last-tile (inc last-tile))
                                   (assoc-in [:board :data idx] replace-tile))}
      :else (do
              (println " Not an anwswer tile clicked " value)
              {}))))



(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
    (assoc-in db/default-db [:board :data] (generate-board))))

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
  click-tile)

;(re-frame/reg-event-fx
;  :win
;  (fn [{:keys [db]} _]
;    (let [{:keys [board score]} db]
;      {:db (assoc-in db [:board :data] (generate-board))})))

(re-frame/reg-event-db
  :win
  (fn [{:as db :keys [score]} _]
    (-> db
        (assoc :next-tile 1 :score (inc score) :last-tile (:last-tile db/default-db))
        (assoc-in [:board :data] (generate-board)))))


(re-frame/reg-event-db
  :start-game
  [check-spec]
  (fn [db _]
    (assoc-in db [:started] true)))

;(re-frame/reg-event-db
;  ::timer)

