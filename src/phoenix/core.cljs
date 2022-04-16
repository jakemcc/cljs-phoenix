(ns phoenix.core
  (:require [clojure.string :as string]))

(defn bind [key modifiers callback]
  (.on js/Key key (clj->js modifiers) callback))

(defn log [& xs]
  (.log js/Phoenix (string/join " " xs)))

(defn notify [^String message]
  (.notify js/Phoenix message))

(defn log-rectangle [prefix rectangle]
  (log prefix
       "x:" (.-x rectangle)
       "y:" (.-y rectangle)
       "width:" (.-width rectangle)
       "half-width:" (/ (.-width rectangle) 2.0)
       "height:" (.-height rectangle)))

(defn debug []
  (log "debug")
  (log-rectangle "Screen's visible frame" (.flippedVisibleFrame (.screen (.focused js/Window))))
  (log-rectangle "App's frame" (.frame (.focused js/Window))))

(defn dbg [x]
  (log x)
  x)

(defn alert [& xs]
  (let [modal (js/Modal.)
        main-screen-rect (.flippedVisibleFrame (.main js/Screen))]
    (set! (.-origin modal) #js {:x (/ (.-width main-screen-rect) 2)
                                :y (/ (.-height main-screen-rect) 2)})
    (set! (.-message modal) (string/join " " xs))
    (set! (.-duration modal) 2)
    (.show modal)))

(defn app-width-adjustment
  [app screen-width]
  (get-in {"iTerm" {1440 8}
           "Emacs" {1440 -4}}
          [(.name app) screen-width]
          0))

(def screen-width-adjustments [0.5 (/ 1 3.0) (/ 2 3.0)])

(defn abs [x]
  (if (< x 0)
    (- x)
    x))

(defn shift [xs]
  (take (count xs)
        (rest (cycle xs))))

(defn next-width-adjustment [window screen-frame]
  (let [window-width (.-width (.size window))
        screen-width (.-width screen-frame)
        current-adjustment (ffirst (sort-by second (for [adjustment screen-width-adjustments
                                                        :let [width (* adjustment screen-width)
                                                              diff-from-window (abs (- width window-width))]]
                                                    [adjustment diff-from-window])))
        curr->next-adjustment (zipmap screen-width-adjustments
                                      (shift screen-width-adjustments))]
    (curr->next-adjustment current-adjustment)))

(defn calc-screen-width
  [width-adjustment window screen-frame]
  (+ (* width-adjustment (.-width screen-frame))
     (app-width-adjustment (.app window) (.-width screen-frame))))

(defn on-left-half? [window screen-frame]
  (= (.-x (.topLeft window))
     (.-x screen-frame)))

(defn at-middle? [window screen-frame]
  (= (.-x (.topLeft window))
     (* 0.25 (.-width screen-frame))))

(defn on-right-half? [window screen-frame]
  (< (abs (- (.-width screen-frame)
             (+ (.-width (.size window))
                (.-x (.topLeft window)))))
     20))

;; TODO: maybe make this vary based on if Zoom is actively doing
;; things, only really want to adjust height if screen sharing
(defn adjust-height [height]
  (- height 5))

(defn to-left-half []
  (when-let [window (.focused js/Window)]
    (let [screen-frame (.flippedVisibleFrame (.screen window))
          width-adjustment (if (on-left-half? window screen-frame)
                             (next-width-adjustment window screen-frame)
                             (first screen-width-adjustments))]
      (.setFrame window #js {:x (.-x screen-frame)
                             :y (.-y screen-frame)
                             :width (calc-screen-width width-adjustment window screen-frame)
                             :height (adjust-height (.-height screen-frame))}))))

(defn to-right-half []
  (when-let [window (.focused js/Window)]
    (let [screen-frame (.flippedVisibleFrame (.screen window))
          width-adjustment (if (on-right-half? window screen-frame)
                             (next-width-adjustment window screen-frame)
                             (first screen-width-adjustments))
          next-width (calc-screen-width width-adjustment window screen-frame)]
      (.setFrame window #js {:x (+ (.-x screen-frame) (- (.-width screen-frame)
                                                         next-width))
                             :y (.-y screen-frame)
                             :width next-width
                             :height (adjust-height (.-height screen-frame))}))))

(defn to-middle []
  (when-let [window (.focused js/Window)]
    (let [screen-frame (.flippedVisibleFrame (.screen window))]
      (.setFrame window #js {:x (+ (.-x screen-frame) (* 0.25 (.-width screen-frame)))
                             :y (.-y screen-frame)
                             :width (calc-screen-width 0.50 window screen-frame)
                             :height (adjust-height (.-height screen-frame))}))))

(defn to-fullscreen []
  (when-let [window (.focused js/Window)]
    (.setFrame window (.flippedVisibleFrame (.screen window)))))


(def round js/Math.round)

(defn move-to-screen [window screen]
  (when (and window screen)
    (let [window-frame (.frame window)
          old-screen-rect (.flippedVisibleFrame (.screen window))
          new-screen-rect (.flippedVisibleFrame screen)
          x-ratio (/ (.-width new-screen-rect) (.-width old-screen-rect))
          y-ratio (/ (.-height new-screen-rect) (.-height old-screen-rect))]
      (.setFrame window #js {:width (round (* x-ratio (.-width window-frame)))
                             :height (round (* y-ratio (.-height window-frame)))
                             :x (+ (round (* (- (.-x window-frame) (.-x old-screen-rect))
                                             x-ratio))
                                   (.-x new-screen-rect))
                             :y (+ (round (* (- (.-y window-frame) (.-y old-screen-rect))
                                             y-ratio))
                                   (.-y new-screen-rect))}))))

(defn left-one-monitor []
  (when-let [window (.focused js/Window)]
    (when-not (= (.screen window) (.next (.screen window)))
      (move-to-screen window (.next (.screen window))))))

(defn right-one-monitor []
  (when-let [window (.focused js/Window)]
    (when-not (= (.screen window) (.previous (.screen window)))
      (move-to-screen window (.previous (.screen window))))))

;; Idea:
;;   search visible windows first, then do minimized windows
;; Below no longer cycles through all the windows. Previous
;;   implementation focused on every window and stayed on last
;;   focused. Now it just focuses on first one returned.
(defn focus-or-start [title]
  (if-let [app (.get js/App title)]
    (let [windows (->> (.windows app) ;; TODO: could probably switch this to visible windows?
                       (remove #(= 1 (.isMinimized %))))]
      (when-not (empty? windows)
        (.focus (first windows))))
    (.launch js/App title #js {:focus true})))

;; Special key on ergodox ez hits all these buttons at once. Use this
;; to not conflict with most other shortcuts.
(def meh-combo ["ctrl" "alt" "shift"])

(defn switch-app [key title]
  (bind key meh-combo (partial focus-or-start title)))

(bind "h" ["alt" "cmd" "ctrl"] debug)

(bind "left" ["alt" "cmd" "ctrl"] left-one-monitor)
(bind "right" ["alt" "cmd" "ctrl"] right-one-monitor)

(bind "left" meh-combo to-left-half)
(bind "right" meh-combo to-right-half)
(bind "f" meh-combo to-fullscreen)
(bind "down" meh-combo to-middle)

(switch-app "c" "iTerm")
(switch-app "i" "IntelliJ IDEA")
(switch-app "e" "Emacs")
(switch-app "b" "Google Chrome")
(switch-app "v" "Brave Browser")
(switch-app "m" "Slack")
(switch-app "k" "KeePassX")
(switch-app "s" "Code")
(switch-app "z" "zoom.us")
(switch-app "o" "Microsoft Outlook")
