(ns phoenix.api)

(def api js/api)

(defn reload [^String path]
  (.reload api path))

(defn launch [^String app-name]
  (.launch api app-name))

(defn alert
  ([^String message]
   (.alert api message))
  ([^String message duration-in-seconds]
   (.alert api message duration-in-seconds)))

(defn cancel-alerts []
  (.cancelAlerts api))

(defn log [^String message]
  (.log api message))

(defn bind [^String key modifiers callback]
  (.bind api key (clj->js modifiers) callback))

(defn run-command [^String command-path arguments]
  (.runCommand api command-path (clj->js arguments)))

(defn set-tint [red green blue]
  (.setTint api (clj->js red) (clj->js green) (clj->js blue)))
