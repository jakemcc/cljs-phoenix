(ns phoenix.app
  (:require [phoenix.api :as api]))

;; Idea:
;;   search visible windows first, then do minimized windows
;; Below no longer cycles through all the windows. Previous
;;   implementation focused on every window and stayed on last
;;   focused. Now it just focuses on first one returned.
(defn focus-or-start [title]
  (if-let [app (.get js/App title)]
    (do
      (let [windows (->> (.windows app)
                         (remove #(= 1 (.isMinimized %))))]
        (if (empty? windows)
          (api/notify (str "All windows minimized for " title))
          (.focus (first windows)))))
    (do
      (api/notify (str "Starting " title))
      (when-let [app (.launch js/App title)]
        (.focus app)))))
