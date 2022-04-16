all: clean build

cljs-%: cljs.jar
	java -cp 'cljs.jar:resources:src' clojure.main $*.clj

build: cljs-build

watch: cljs-watch

release: clean cljs-release

install: release
	cp out/main.js ~/.phoenix.js

clean:
	rm -rf out

cljs.jar:
	curl -L https://github.com/clojure/clojurescript/releases/download/r1.11.4/cljs.jar > cljs.jar
	touch cljs.jar



