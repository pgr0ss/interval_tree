# interval_tree

A simple implementation of an interval tree: https://en.wikipedia.org/wiki/Interval_tree

## Usage

```clojure
(ns your-app
  (:require [interval-tree.core :as tree]))

(def t (new-tree [5 10] [3 12] [11 12] [1 2] [4 6] [15 20] [3 10]))

(includes-point t 5)
;; ([3 10] [3 12] [4 6] [5 10])

(includes-point t 3)
;; ([3 10] [3 12])

(includes-point t 0)
;; ()
```

## License

Copyright © 2014 Paul Gross

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
