all: clean build

cljs-%: cljs.jar
	java -cp 'cljs.jar:resources:src' clojure.main $*.clj

build: cljs-build

watch: cljs-watch

release: clean cljs-release

clean:
	rm -rf out

cljs.jar:
	curl -L https://github.com/clojure/clojurescript/releases/download/r1.10.866/cljs.jar > cljs.jar
	touch cljs.jar



