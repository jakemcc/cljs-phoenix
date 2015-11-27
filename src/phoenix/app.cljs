(ns phoenix.app
  (:require [phoenix.api :as api]))

(defn all-with-title [title]
  (filter (fn [app]
            (= title (.title app)))
          (.runningApps js/App)))

(defn focus-or-start [title]
  (let [apps (all-with-title title)]
    (if (empty? apps)
      (do
        (api/alert (str "Starting " title))
        (api/launch title))
      (do
        (let [windows (->> apps
                           ;; could probably use all visible windows
                           (mapcat #(.allWindows %))
                           (remove #(= 1 (.isWindowMinimized %))))]
          (if (empty? windows)
            (api/alert (str "All windows minimized for " title))
            (do (api/alert (str "Switch to window " (.title (first windows))))
                (.focusWindow (first windows)))))))))
