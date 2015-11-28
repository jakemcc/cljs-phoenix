(ns phoenix.api)

(defn bind [^String key modifiers callback]
  (.bind js/Phoenix key (clj->js modifiers) callback))

(defn notify [^String message]
  (.notify js/Phoenix message))
