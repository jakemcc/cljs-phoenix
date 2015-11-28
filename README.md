# cljs-phoenix

ClojureScript configuration for version 2.0 of the OS X window manager
[Phoenix](https://github.com/kasper/phoenix).

# Background

[Phoenix](https://github.com/kasper/phoenix) is a window manager that
provides a JavaScript API for managing your windows in OS X. I've
[been a user](http://www.jakemccrary.com/blog/2014/03/30/managing-windows-in-osx-using-phoenix/)
of Phoenix from its early days and highly recommend it.

For the 1.* versions of Phoenix I had my configuration written in
JavaScript. With the release of version 2.0 I decided to try to write
my configuration using
[ClojureScript](https://github.com/clojure/clojurescript).

Prior to this I've used ClojureScript for building production user
interfaces but have always use [Leiningen](http://leiningen.org/) and
[lein-cljsbuild](https://github.com/emezeske/lein-cljsbuild) for
building my projects. This project was used to learn what it takes to
not use those tools. It was also used to further my understanding of
what needs to happen to use a foreign JavaScript library with
ClojureScript's advanced compilation mode.

# Using

## Requirements

- Java 8 is installed on your machine.
- You're using Phoenix 2.0.

## Building

1. `make cljs-compiler` - downloads `cljs.jar` to your local project
1. `make release` - Builds `out/main.js`. This is your `~/.phoenix.js`
   file.

## Developing

`make watch` watches for changes and builds `out/main.js` whenever a
ClojureScript file changes. It is recommended to symlink
`~/.phoenix.js` to this project's `out/main.js` in order to have
Phoenix pick up configuration changes and new builds finish.


