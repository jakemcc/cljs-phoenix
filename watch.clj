(require 'cljs.build.api)

(cljs.build.api/watch "src"
                      {:output-to "out/main.js"
                       :optimizations :simple
                       :main 'phoenix.core
                       ;; :optimizations :advanced
                       ;; :optimize-constants false
                       ;; :target :nodejs
                       :externs ["phoenix-externs.js"]})
