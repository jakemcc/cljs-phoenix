(ns phoenix.api)

(defn reload [^String path]
  (.reload js/Phoenix path))

(defn bind [^String key modifiers callback]
  (.bind js/Phoenix key (clj->js modifiers) callback))

(defn log [^String message]
  (.log js/Phoenix message))

(defn notify [^String message]
  (.notify js/Phoenix message))

(defn alert
  [^String message]
  (.log js/Phoenix message))

