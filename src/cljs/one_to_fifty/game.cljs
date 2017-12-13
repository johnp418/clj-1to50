(ns one-to-fifty.game)

;(def row-n 5)
;(def col-n 5)

(def start-tile 1)
(def end-tile 60)

(defn new-board []
  (shuffle (range start-tile end-tile)))

