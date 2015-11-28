all: clean build

cljs-%:
	java -cp 'cljs.jar:resources:src' clojure.main $*.clj

build: cljs-build

watch: cljs-watch

repl: cljs-repl

release: clean cljs-release

clean:
	rm -rf out

lib:
	mkdir -p lib

cljs-compiler:
	curl -L https://github.com/clojure/clojurescript/releases/download/r1.7.170/cljs.jar > cljs.jar


