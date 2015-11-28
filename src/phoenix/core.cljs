(ns phoenix.core
  (:require [clojure.string :as string]))

(defn bind [key modifiers callback]
  (.bind js/Phoenix key (clj->js modifiers) callback))

(defn log [& xs]
  (.log js/Phoenix (string/join " " xs)))

(defn log-rectangle [prefix rectangle]
  (log prefix
       "x:" (.-x rectangle)
       "y:" (.-y rectangle)
       "width:" (.-width rectangle)
       "half-width:" (/ (.-width rectangle) 2.0)
       "height:" (.-height rectangle)))

(defn debug []
  (log "debug")
  (log-rectangle "Screen's visible frame" (.visibleFrameInRectangle (.screen (.focusedWindow js/Window))))
  (log-rectangle "App's frame" (.frame (.focusedWindow js/Window))))

(defn dbg [x]
  (log x)
  x)

(defn app-width-adjustment
  [app screen-width]
  (get-in {"iTerm" {1440 8}
           "Emacs" {1440 -4}}
          [(.name app) screen-width]
          0))

(defn to-left-half []
  (when-let [window (.focusedWindow js/Window)]
    (let [screen-frame (.visibleFrameInRectangle (.screen window))]
      (.setFrame window #js {:x (.-x screen-frame)
                             :y (.-y screen-frame)
                             :width (+ (* 0.5 (.-width screen-frame))
                                       (app-width-adjustment (.app window) (.-width screen-frame)))
                             :height (.-height screen-frame)}))))

(defn to-right-half []
  (when-let [window (.focusedWindow js/Window)]
    (let [screen-frame (.visibleFrameInRectangle (.screen window))]
      (.setFrame window #js {:x (+ (.-x screen-frame) (* 0.5 (.-width screen-frame)))
                             :y (.-y screen-frame)
                             :width (+ (* 0.5 (.-width screen-frame))
                                       (app-width-adjustment (.app window) (.-width screen-frame)))
                             :height (.-height screen-frame)}))))

(defn to-fullscreen []
  (when-let [window (.focusedWindow js/Window)]
    (.setFrame window (.visibleFrameInRectangle (.screen window)))))


(def round js/Math.round)

(defn move-to-screen [window screen]
  (when (and window screen)
    (let [window-frame (.frame window)
          old-screen-rect (.visibleFrameInRectangle (.screen window))
          new-screen-rect (.visibleFrameInRectangle screen)
          x-ratio (/ (.-width new-screen-rect) (.-width old-screen-rect))
          y-ratio (/ (.-height new-screen-rect) (.-height new-screen-rect))]
      (.setFrame window #js {:width (round (* x-ratio (.-width window-frame)))
                             :height (round (* y-ratio (.-height window-frame)))
                             :x (+ (round (* (- (.-x window-frame) (.-x old-screen-rect))
                                             x-ratio))
                                   (.-x new-screen-rect))
                             :y (+ (round (* (- (.-y window-frame) (.-y old-screen-rect))
                                             y-ratio))
                                   (.-y new-screen-rect))}))))

(defn left-one-monitor []
  (when-let [window (.focusedWindow js/Window)]
    (when-not (= (.screen window) (.next (.screen window)))
      (move-to-screen window (.next (.screen window))))))

(defn right-one-monitor []
  (when-let [window (.focusedWindow js/Window)]
    (when-not (= (.screen window) (.previous (.screen window)))
      (move-to-screen window (.previous (.screen window))))))

(defn switch-app [key name]
  (bind key ["cmd" "ctrl"] (fn [] (.focus (.launch js/App name)))))

;; Per Phoenix docs, need to capture results of
;; Phoenix.bind to GC doesn't clean them up.
(def ^:export bound-keys
  [(bind "h" ["alt" "cmd" "ctrl"] debug)

   (bind "left" ["alt" "cmd" "ctrl"] left-one-monitor)
   (bind "right" ["alt" "cmd" "ctrl"] right-one-monitor)

   (bind "left" ["alt" "cmd"] to-left-half)
   (bind "right" ["alt" "cmd"] to-right-half)
   (bind "f" ["alt" "cmd"] to-fullscreen)

   (switch-app "c" "iTerm")
   (switch-app "e" "Emacs")
   (switch-app "b" "Google Chrome")
   (switch-app "f" "Firefox")
   (switch-app "m" "Slack")
   (switch-app "r" "Rdio")
   (switch-app "k" "KeePassX")])
