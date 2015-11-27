
all: clean build

build:
	java -cp 'cljs.jar:resources:src' clojure.main build.clj

watch:
	java -cp cljs.jar:src clojure.main watch.clj

repl:
	rlwrap java -cp cljs.jar:src clojure.main repl.clj

release:
	java -cp cljs.jar:src clojure.main release.clj

node:
	java -cp cljs.jar:src clojure.main node.clj

node-repl:
	rlwrap java -cp cljs.jar:src clojure.main node_repl.clj

clean:
	rm -rf out

lib:
	mkdir -p lib

cljs-compiler:
	curl -L https://github.com/clojure/clojurescript/releases/download/r1.7.170/cljs.jar > cljs.jar


