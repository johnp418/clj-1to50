(ns one-to-fifty.game)


(def cols 5)
(def win-tile (-> (* cols cols)
                  (* 2)))

(defn generate-board []
  (vec
    (->> (drop 1 (range))
         (take (* cols cols))
         (shuffle))))

(def initial-state
  {:score 0
   :rows cols
   :cols cols
   :timer nil
   :next-tile 1
   :started false
   :win-tile win-tile
   :last-tile (* cols cols)
   :board {:cols cols :data (generate-board)}})

