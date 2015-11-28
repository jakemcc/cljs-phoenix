(ns phoenix.core
  (:require [phoenix.api :as api]
            [clojure.string :as string]))

(defn log [& xs]
  (.log js/Phoenix (string/join " " xs)))

(defn log-rectangle [prefix rectangle]
  (log prefix
       "x:" (.-x rectangle)
       "y:" (.-y rectangle)
       "width:" (.-width rectangle)
       "height:" (.-height rectangle)))

(defn dbg [x]
  (log x)
  x)

(defn app-width-adjustment [app]
  (get {"iTerm" 8
        "Emacs" -4}
       (.name app)
       0))

(defn to-left-half []
  (when-let [window (.focusedWindow js/Window)]
    (let [screen-frame (.visibleFrameInRectangle (.screen window))]
      (.setFrame window (clj->js {:x (.-x screen-frame)
                                  :y (.-y screen-frame)
                                  :width (+ (* 0.5 (.-width screen-frame))
                                            (app-width-adjustment (.app window)))
                                  :height (.-height screen-frame)})))))

(defn to-right-half []
  (when-let [window (.focusedWindow js/Window)]
    (let [screen-frame (.visibleFrameInRectangle (.screen window))]
      (.setFrame window (clj->js {:x (+ (.-x screen-frame) (* 0.5 (.-width screen-frame)))
                                  :y (.-y screen-frame)
                                  :width (+ (* 0.5 (.-width screen-frame))
                                            (app-width-adjustment (.app window)))
                                  :height (.-height screen-frame)})))))

(defn to-fullscreen []
  (when-let [window (.focusedWindow js/Window)]
    (.setFrame window (.visibleFrameInRectangle (.screen window)))))

(defn switch-app [key name]
  (api/bind key ["cmd" "ctrl"] (fn [] (.focus (.launch js/App name)))))

(def round js/Math.round)

(defn move-to-screen [window screen]
  (when (and window screen)
    (let [window-frame (.frame window)
          old-screen-rect (.visibleFrameInRectangle (.screen window))
          new-screen-rect (.visibleFrameInRectangle screen)
          x-ratio (/ (.-width new-screen-rect) (.-width old-screen-rect))
          y-ratio (/ (.-height new-screen-rect) (.-height new-screen-rect))
          new-frame {:width (round (* x-ratio (.-width window-frame)))
                     :height (round (* y-ratio (.-height window-frame)))
                     :x (+ (round (* (- (.-x window-frame) (.-x old-screen-rect))
                                     x-ratio))
                           (.-x new-screen-rect))
                     :y (+ (round (* (- (.-y window-frame) (.-y old-screen-rect))
                                     y-ratio))
                           (.-y new-screen-rect))}]
      (.setFrame window (clj->js new-frame)))))

(defn left-one-monitor []
  (when-let [window (.focusedWindow js/Window)]
    (when-not (= (.screen window) (.next (.screen window)))
      (move-to-screen window (.next (.screen window))))))

(defn right-one-monitor []
  (when-let [window (.focusedWindow js/Window)]
    (when-not (= (.screen window) (.previous (.screen window)))
      (move-to-screen window (.previous (.screen window))))))

(defn debug []
  (log "debug")
  (log-rectangle "visible frame" (.visibleFrameInRectangle (.mainScreen js/Screen)))
  (log-rectangle "window" (.frame (.focusedWindow js/Window))))

;; Per Phoenix docs, need to capture results of
;; Phoenix.bind to GC doesn't clean them up.
(def ^:export bound-keys
  [(api/bind "h" ["alt" "cmd" "ctrl"] debug)

   (api/bind "left" ["alt" "cmd" "ctrl"] left-one-monitor)
   (api/bind "right" ["alt" "cmd" "ctrl"] right-one-monitor)

   (api/bind "left" ["alt" "cmd"] to-left-half)
   (api/bind "right" ["alt" "cmd"] to-right-half)
   (api/bind "f" ["alt" "cmd"] to-fullscreen)

   (switch-app "c" "iTerm")
   (switch-app "e" "Emacs")
   (switch-app "b" "Google Chrome")
   (switch-app "f" "Firefox")
   (switch-app "m" "Slack")
   (switch-app "r" "Rdio")
   (switch-app "k" "KeePassX")])
