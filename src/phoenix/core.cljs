(ns phoenix.core
  (:require [phoenix.api :as api]
            [phoenix.app :as app]))

(def cmd-ctrl ["cmd" "ctrl"])

(api/bind "c" cmd-ctrl (fn [] (app/focus-or-start "iTerm")))
