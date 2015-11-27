(ns phoenix.app
  (:require [phoenix.api :as api]))

(def App js/App)

(defn all-with-title [title]
  (filter (fn [app]
            (= title (.title app)))
          (App/runningApps)))

(defn focus-or-start [title]
  (let [apps (all-with-title title)]
    (if (empty? apps)
      (do
        (api/alert (str "Starting " title))
        (api/launch title))
      (do
        (api/alert (str "Switching to " title))
        (let [windows (->> apps
                           ;; could probably use all visible windows
                           (mapcat #(.allWindows %))
                           (remove #(= 1 (.isWindowMinimized %))))]
          (if (empty? windows)
            (api/alert (str "All windows minimized for " title))
            (.focusWindow (first windows))))))))
