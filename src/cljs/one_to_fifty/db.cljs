(ns one-to-fifty.db
  (:require [one-to-fifty.main.core :refer [win-tile cols]]
            [cljs.spec.alpha :as s]))


(s/def ::score int?)
(s/def ::next-tile int?)
(s/def ::started boolean?)
(s/def ::win-tile int?)
(s/def ::last-tile int?)


(s/def ::score-items (s/* ::score))

(s/def ::data #(instance? PersistentVector %))
(s/def ::cols int?)

; TODO: :req-un ?
(s/def ::board (s/keys :req-un [::cols ::data]))


(s/def ::db (s/keys :req-un [::score
                             ::next-tile
                             ::started
                             ::win-tile
                             ::last-tile
                             ::board]))


;(s/describe ::board)
;
;(s/exercise ::score-items 10)
;
;(comment
;  (s/exercise ::board 1))


(def default-db
  {:score     0
   :timer     nil
   :next-tile 1
   :started   false
   :win-tile  win-tile
   :last-tile (* cols cols)
   :board     {:cols cols :data nil}})


