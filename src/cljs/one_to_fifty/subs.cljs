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
  ::board
  (fn [db _]
    (:board db)))

;(re-frame/reg-sub
;  ::board-rows
;  (fn [db _]
;    (let [{:keys [row-n col-n board]} db]
;      (partition col-n (take 25 board)))))

(re-frame/reg-sub
  ::next-tile
  (fn [db _]
    (:next-tile db)))

(re-frame/reg-sub
  ::score
  (fn [db _]
    (:score db)))


(re-frame/reg-sub
  ::timer
  (fn [db _]
    (:timer db)))

(re-frame/reg-sub
  ::started
  (fn [db _]
    (:started db)))