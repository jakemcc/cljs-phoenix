(ns phoenix.core
  (:require [phoenix.api :as api]
            [phoenix.app :as app]))

(defn to-left-half []
  (when-let [window (.focusedWindow js/Window)]
    (let [screen-frame (.visibleFrameInRectangle (.screen window))]
      (.setFrame window (clj->js {:x (.-x screen-frame)
                                  :y (.-y screen-frame)
                                  :width (* 0.5 (.-width screen-frame))
                                  :height (.-height screen-frame)})))))

(defn to-right-half []
  (when-let [window (.focusedWindow js/Window)]
    (let [screen-frame (.visibleFrameInRectangle (.screen window))]
      (.setFrame window (clj->js {:x (+ (.-x screen-frame) (* 0.5 (.-width screen-frame)))
                                  :y (.-y screen-frame)
                                  :width (* 0.5 (.-width screen-frame))
                                  :height (.-height screen-frame)})))))

(defn to-fullscreen []
  (when-let [window (.focusedWindow js/Window)]
    (.setFrame window (.visibleFrameInRectangle (.screen window)))))

(defn switch-app [key title]
  (api/bind key ["cmd" "ctrl"] (fn [] (app/focus-or-start title))))

(def ^:export bound-keys
  [(api/bind "left" ["alt" "cmd"] to-left-half)
   (api/bind "right" ["alt" "cmd"] to-right-half)
   (api/bind "f" ["alt" "cmd"] to-fullscreen)
   (switch-app "c" "iTerm")
   (switch-app "e" "Emacs")
   (switch-app "b" "Google Chrome")
   (switch-app "f" "Firefox")
   (switch-app "m" "Slack")
   (switch-app "r" "Rdio")
   (switch-app "k" "KeePassX")
   ])
