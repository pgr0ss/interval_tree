(ns interval-tree.core)

(defn- left-of-x-center [x-center intervals]
  (filter #(neg? (compare (last %) x-center)) intervals))

(defn- right-of-x-center [x-center intervals]
  (filter #(pos? (compare (first %) x-center)) intervals))

(defn- intersecting-x-center [x-center intervals]
  (filter #(and (<= (compare (first %) x-center) 0)
                (>= (compare (last %) x-center) 0)) intervals))

(defn new-tree [& intervals]
  (let [sorted-intervals (sort intervals)
        number-of-intervals (count intervals)
        x-center (first (nth sorted-intervals (/ number-of-intervals 2)))
        left-of-center (left-of-x-center x-center sorted-intervals)
        intersecting-center (intersecting-x-center x-center sorted-intervals)
        right-of-center (right-of-x-center x-center sorted-intervals)
        left-tree (if-not (empty? left-of-center) (apply new-tree left-of-center))
        right-tree (if-not (empty? right-of-center) (apply new-tree right-of-center))]
    {:x-center x-center
     :intersecting-x-center intersecting-center
     :left-tree left-tree
     :right-tree right-tree}))

(defn- in-interval? [point interval]
  (and (>= (compare point (first interval)) 0)
       (<= (compare point (second interval)) 0)))

(defn includes-point [tree point]
  (let [matching-this-node (filter (partial in-interval? point)
                                   (:intersecting-x-center tree))
        matching-left (if (and (neg? (compare point (:x-center tree)))
                               (some? (:left-tree tree)))
                        (includes-point (:left-tree tree) point))
        matching-right (if (and (pos? (compare point (:x-center tree)))
                                (some? (:right-tree tree)))
                         (includes-point (:right-tree tree) point))]
    (concat matching-this-node matching-left matching-right)))
