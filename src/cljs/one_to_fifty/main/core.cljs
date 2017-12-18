(ns one-to-fifty.main.core)

(def cols 5)
(def win-tile (-> (* cols cols)
                  (* 2)))

(defn generate-board []
  (vec
    (->> (drop 1 (range))
         (take (* cols cols))
         (shuffle))))

