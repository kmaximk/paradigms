(defn op [f] (fn [& args] (fn [x] (apply f (mapv #(% x) args)))))
(def add (op +))
(def subtract (op (eval -)))
(def multiply (op *))
(defn divide [a b] (fn [m] (/ (double (a m)) (double (b m)))))
(def negate (op -))
(def ln (op #(Math/log (abs %))))
(def exp (op #(Math/exp %)))
(defn constant [x] (fn [m] x))
(defn variable [x] (fn [m] (get-in m [x])))

(load-file "proto.clj")

(def Proto
  {
   :evaluate        (fn [this vars] (apply
                                      (proto-get this :f)
                                      (mapv (fn [x] (proto-call x :evaluate vars)) (this :args))
                                      ))
   :toString        (fn [this] (str
                                 (reduce
                                   (fn [curr elem] (str curr " " (proto-call elem :toString)))
                                   (str "(" (proto-get this :sign))
                                   (this :args))
                                 ")"))
   :toStringPostfix (fn [this] (str
                                 (reduce
                                   (fn [curr elem] (str curr (proto-call elem :toStringPostfix) " "))
                                   (str "(")
                                   (this :args))
                                 (proto-get this :sign) ")"))
   })
(defn expressionFactory [f sign]
  (constructor
    (fn [this & args] (assoc this :x (first args) :args args))
    (assoc Proto :f f :sign sign)))

(def Add (expressionFactory + "+"))
(def Divide (expressionFactory #(/ (double %1) (double %2)) "/"))
(def Multiply (expressionFactory * "*"))
(def Subtract (expressionFactory - "-"))
(def Negate (expressionFactory - "negate"))

(defn const-var [evaluate]
  (constructor #(assoc %1 :x %2) {:evaluate        evaluate
                                  :toString        (fn [this] (str (proto-get this :x)))
                                  :toStringPostfix (fn [this] (str (proto-get this :x)))
                                  }))
(def Constant (const-var (fn [this _] (proto-get this :x))))

(def Variable (const-var (fn [this vars] (get vars (clojure.string/lower-case (str (nth (proto-get this :x) 0)))))))
(def Sin (expressionFactory #(Math/sin %) "sin"))
(def Cos (expressionFactory #(Math/cos %) "cos"))
(def Inc (expressionFactory inc "++"))
(def Dec (expressionFactory dec "--"))

(defn evaluate [this vars] (proto-call this :evaluate vars))
(defn toString [this] (proto-call this :toString))
(defn toStringPostfix [this] (proto-call this :toStringPostfix))

(def map-objects {
                  '+        Add
                  '*        Multiply
                  '-        Subtract
                  '/        Divide
                  'negate   Negate
                  'sin      Sin
                  'cos      Cos
                  '++       Inc
                  '--       Dec
                  :CONSTANT Constant
                  :VARIABLE Variable
                  })

(def map-func {
               '+        add
               '*        multiply
               '-        subtract
               '/        divide
               'negate   negate
               'ln       ln
               'exp      exp
               :CONSTANT constant
               :VARIABLE variable
               })

(defn par [map y]
  (cond
    (list? y) (apply (get-in map [(nth y 0)]) (mapv (partial par map) (rest y)))
    (number? y) ((get-in map [:CONSTANT]) y)
    :else ((get-in map [:VARIABLE]) (name y))))
(defn parseObject [x] (par map-objects (read-string x)))
(defn parseFunction [x] (par map-func (read-string x)))

(load-file "parser.clj")
(def parseNum
  (+map
    (comp Constant read-string (fn [x] (str (first x) (apply str (second x)))))
    (+seq (+opt (+char "-"))
          (+plus (+or
                   (+char ".0123456789")
                   )))))
(def variables
  (+map (comp Variable (partial apply str)) (+plus (+char "XYZxyz"))))
(def skipWs (+ignore (+star (+char " "))))
(defn createSeq [x] (apply +seq (mapv (comp +char str) (sequence x))))
(def oper
  (+map
    (comp
      (partial get map-objects)
      symbol
      (fn [x] (if (vector? x) (apply str x) (str x))))
    (+or
      (createSeq "++")
      (createSeq "--")
      (+char "+-*/")
      (createSeq "negate")
      (createSeq "sin")
      (createSeq "inc")
      (createSeq "dec"))
    )
  )
(def combParser
  ;(+parser
  (+map (fn [a] (if (> 2 (count (first a)))
                  ((nth a 1) (first (first (first a))))
                  (apply (peek a) (mapv (fn [x] (first x)) (first a)))))
        (+seq
          (+ignore (+char "("))
          skipWs
          (+plus
            (+seq
              (+or
                (delay combParser)
                parseNum
                variables
                )
              skipWs
              ))
          oper
          skipWs
          (+ignore (+char ")"))
          )))
(defn parseObjectPostfix [str] (first ((+parser(+seq
                                               (+ignore (+star (+char " ")))
                                               (+or combParser
                                                    parseNum
                                                    variables)
                                               (+ignore (+star (+char " "))))) str)))

