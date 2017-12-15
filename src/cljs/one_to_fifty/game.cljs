(ns one-to-fifty.game)


(def cols 5)
(def win-tile (-> (* cols cols)
                  (* 2)))


(defn generate []
  (partition cols (->> (drop 1 (range))
                      (take (* cols cols))
                      (shuffle))))

(defn new-board []
  (let [board (generate)
        map-col (fn [row]
                  (fn [k v]
                    {:row row :col k :value v}))
        map-row (fn [row_i row]
                  (vec (map-indexed (map-col row_i) row)))
        new (vec (map-indexed map-row board))]
    ;(println "Board =>" new)
    new))

;(defn new-board []
;  )

(def initial-state
  {:name  "1 to 50"
   :score 0
   :timer nil
   :next-tile 1
   :started false
   :win-tile win-tile
   :last-tile (* cols cols)
   :board (new-board)})
