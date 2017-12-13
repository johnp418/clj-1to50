(ns one-to-fifty.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(re-frame/reg-sub
  ::board-state
  (fn [db _]
    (:board db)))

(re-frame/reg-sub
  ::board-rows
  (fn [db _]
    (let [{:keys [row-n col-n board]} db]
      (partition col-n board))))

(re-frame/reg-sub
  ::match-tile
  (fn [db _]
    (:last-tile db)))
;
;(re-frame/reg-sub
;  ::add-tile
;  )