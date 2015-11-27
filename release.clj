(require 'cljs.build.api)

(cljs.build.api/build "src"
                      {:output-to "out/main.js"
                       :optimizations :advanced
                       :main 'phoenix.core
                       :externs ["phoenix-externs.js"]})

(System/exit 0)
