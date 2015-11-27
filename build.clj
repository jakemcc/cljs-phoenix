(require 'cljs.build.api)

(cljs.build.api/build "src"
                      {:output-to "out/main.js"
                       :optimizations :advanced
                       :externs ["phoenix-externs.js"]})
