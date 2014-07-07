(ns interval-tree.core-test
  (:require [clojure.test :refer :all]
            [interval-tree.core :as tree]))

(deftest tree
  (testing "single interval"
    (let [t (tree/new-tree [5 10])]
      (is (empty? (tree/includes-point t 3)))
      (is (= [[5 10]] (tree/includes-point t 5)))
      (is (= [[5 10]] (tree/includes-point t 7)))
      (is (= [[5 10]] (tree/includes-point t 10)))
      (is (empty? (tree/includes-point t 12)))))

  (testing "overlapping intervals"
    (let [t (tree/new-tree [5 10] [7 12])]
      (is (= [[5 10]] (tree/includes-point t 6)))
      (is (= [[5 10] [7 12]] (tree/includes-point t 7)))
      (is (= [[7 12]] (tree/includes-point t 11)))))

  (testing "subset intervals"
    (let [t (tree/new-tree [5 10] [3 12] [11 12])]
      (is (= [[3 12] [5 10]] (tree/includes-point t 6)))
      (is (= [[3 12] [11 12]] (tree/includes-point t 11)))
      (is (empty? (tree/includes-point t 14)))))

  (testing "lots of intervals"
    (let [t (tree/new-tree [5 10] [3 12] [11 12] [1 2] [4 6] [15 20] [3 10])]
      (is (= [[3 10] [3 12] [4 6] [5 10]] (tree/includes-point t 6)))
      (is (= [[1 2]] (tree/includes-point t 1)))))

  (testing "works with strings"
    (let [t (tree/new-tree ["hello" "world"] ["apple" "mango"] ["pear" "yuca"])]
      (is (= [["apple" "mango"] ["hello" "world"]] (tree/includes-point t "lemon")))
      (is (= [["apple" "mango"]] (tree/includes-point t "banana"))))))
