(ns phoenix.core
  (:require [phoenix.api :as api]
            [phoenix.app :as app]))

(def cmd-ctrl ["cmd" "ctrl"])

(defn switch-app [key title]
  (api/bind key cmd-ctrl (fn [] (app/focus-or-start title))))

(def bound-keys
  [(switch-app "c" "iTerm")
   (switch-app "e" "Emacs")
   (switch-app "b" "Google Chrome")
   (switch-app "f" "Firefox")
   (switch-app "m" "Slack")
   (switch-app "r" "Rdio")
   (switch-app "k" "KeePassX")])
