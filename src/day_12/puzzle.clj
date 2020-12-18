(ns day-12.puzzle
  (:require [santas-little-helpers :as h]))

(def input (->> (h/read-input "data/day_12/input.txt")
                (map (fn [s]
                       (let [direction (drop 1 (flatten (re-seq #"(\w)(\d+)" s)))
                             bearing   (first direction)
                             delta     (last direction)]
                         [bearing (h/parse-int delta)])))))

(def test-input [["F" 10] ["N" 3] ["F" 7] ["R" 90] ["F" 11]])

(defn navigate-ship
  [direction position facing]
  (let [[bearing delta]   direction
        relative-facing   (mod facing 360)
        facing-directions {0   (fn [[x y] delta] [(+ x delta) y])
                           90  (fn [[x y] delta] [x (- y delta)])
                           180 (fn [[x y] delta] [(- x delta) y])
                           270 (fn [[x y] delta] [x (+ y delta)])}
        cardinal-directions {"N" (fn [[x y] delta] [x (+ y delta)])
                             "S" (fn [[x y] delta] [x (- y delta)])
                             "E" (fn [[x y] delta] [(+ x delta) y])
                             "W" (fn [[x y] delta] [(- x delta) y])}
        navigate-forward (get facing-directions relative-facing)
        navigate-by-direction (get cardinal-directions bearing)]
    (case bearing
      "F" [(navigate-forward position delta) facing]
      "N" [(navigate-by-direction position delta) facing]
      "S" [(navigate-by-direction position delta) facing]
      "E" [(navigate-by-direction position delta) facing]
      "W" [(navigate-by-direction position delta) facing]
      "R" [position (+ facing delta)]
      "L" [position (- facing delta)])))

(defn rotate-waypoint
  [waypoint degrees]
  (let [[wp-x wp-y] waypoint]
    (case (mod degrees 360)
      0   [wp-x wp-y]
      90  [wp-y (* wp-x -1)]
      180 [(* wp-x -1) (* wp-y -1)]
      270 [(* wp-y -1) wp-x])))

(defn move-to-waypoint
  [delta ship-pos waypoint]
  (let [[ship-x ship-y] ship-pos
        [wp-x wp-y]     waypoint
        [new-x new-y]     [(+ ship-x (* wp-x delta)) (+ ship-y (* wp-y delta))]]
    [new-x new-y]))

(defn navigate-ship-or-waypoint
  [direction ship-pos waypoint]
  (let [[bearing delta] direction
        [wp-x wp-y]     waypoint]
    (case bearing
      "F" {:ship-pos (move-to-waypoint delta ship-pos waypoint) :waypoint waypoint}
      "N" {:ship-pos ship-pos :waypoint [wp-x (+ wp-y delta)]}
      "E" {:ship-pos ship-pos :waypoint [(+ wp-x delta) wp-y]}
      "S" {:ship-pos ship-pos :waypoint [wp-x (- wp-y delta)]}
      "W" {:ship-pos ship-pos :waypoint [(- wp-x delta) wp-y]}
      "R" {:ship-pos ship-pos :waypoint (rotate-waypoint waypoint delta)}
      "L" {:ship-pos ship-pos :waypoint (rotate-waypoint waypoint (* delta -1))})))

(comment
;; solution pt1
  (let [navigated (reduce (fn [acc direction]
                            (let [[new-position new-facing] (navigate-ship direction (:position acc) (:facing acc))]
                              (assoc acc :position new-position
                                     :facing new-facing)))
                          {:position [0 0] :facing 0} input)]
    (apply + (map h/abs (:position navigated))))

;;solution pt2 
  (let [navigated (reduce (fn [{:keys [ship-pos waypoint] :as acc} direction]
                            (let [new-positions (navigate-ship-or-waypoint direction ship-pos waypoint)]
                              (assoc acc :ship-pos (:ship-pos new-positions)
                                     :waypoint (:waypoint new-positions))))
                          {:ship-pos [0 0] :waypoint [10 1]} input)]
    (apply + (map h/abs (:ship-pos navigated)))))
