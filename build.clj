(require 'cljs.build.api)

(cljs.build.api/build "src"
                      {:output-to "out/main.js"
                       :main 'phoenix.core
                       :optimizations :advanced
                       :externs ["phoenix-externs.js"]})
