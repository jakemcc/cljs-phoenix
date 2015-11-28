(ns phoenix.app
  (:require [phoenix.api :as api]))

(defn all-with-title [title]
  (filter (fn [app] (= title (.name app)))
          (.runningApps js/App)))


;; Idea:
;;   search visible windows first, then do minimized windows
(defn focus-or-start [title]
  (let [apps (all-with-title title)]
    (if (empty? apps)
      (do
        (api/notify (str "Starting " title))
        (.launch js/App title))
      (do
        (let [windows (->> apps
                           ;; could probably use all visible windows
                           (mapcat #(.windows %))
                           (remove #(= 1 (.isMinimized %))))]
          (if (empty? windows)
            (api/notify (str "All windows minimized for " title))
            (do ;;(api/log (str "Switch to window " (.title (first windows))))
                (.focus (first windows)))))))))
