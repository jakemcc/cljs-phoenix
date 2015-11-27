(require 'cljs.build.api)

(cljs.build.api/watch "src"
                      {:output-to "out/main.js"
                       :optimizations :simple
                       :main 'phoenix.core
                       :externs ["phoenix-externs.js"]})
