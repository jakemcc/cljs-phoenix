(ns phoenix.core
  (:require [phoenix.api :as api]))

(api/bind "c" ["ctrl" "cmd"] (fn [] (api/alert "Hello World!")))
