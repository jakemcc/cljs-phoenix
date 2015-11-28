(ns phoenix.api)

(defn bind [^String key modifiers callback]
  (.bind js/Phoenix key (clj->js modifiers) callback))

(defn log [^String message]
  (.log js/Phoenix message))

(defn notify [^String message]
  (.notify js/Phoenix message))
