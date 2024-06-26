(defn v+ [& args] (apply mapv + args))
(defn v- [x y] (mapv - x y))
(defn v* [x y] (mapv * x y))
(defn vd [x y] (mapv / x y))
(defn scalar [x y] (reduce + (mapv * x y)))
(defn -* [x y i j] (- (* (x i) (y j)) (* (x j) (y i))))
(defn vect [x y] (vector (-* x y 1 2) (-* x y 2 0) (-* x y 0 1)))
(defn v*s [x s] (mapv (partial * s) x))
(defn mOp [x y f] (mapv (fn [a b] (mapv f a b)) x y))
(defn mOp2 [x f] (mapv (fn [a] (f a)) x))
(defn anyVec [op n1 n2]  (if (number? n1) (op n1 n2) (mapv (partial anyVec op) n1 n2)))
(defn s+ [x y] (anyVec + x y))
(defn s- [x y] (anyVec - x y))
(defn s* [x y] (anyVec * x y))   
(defn sd [x y] (anyVec / x y))
(defn m- [x y] (mOp x y -))
(defn m+ [x y] (mOp x y +))
(defn m* [x y] (mOp x y *))
(defn md [x y] (mOp x y /))
(defn transpose [m] (apply mapv vector m))
(defn m*m [x y] (mapv (fn [a] (mapv (fn [b] (scalar a b)) (transpose y))) x))
(defn m*v [x y] (mOp2 x (partial scalar y)))
(defn m*s [x s] (mapv (fn [a] (v*s a s)) x))
