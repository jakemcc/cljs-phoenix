(require 'cljs.build.api)

(cljs.build.api/watch "src"
                      {:output-to "out/main.js"
                       :optimizations :simple
                       :externs ["phoenix-externs.js"]})
