lein run -m shadow.cljs.devtools.cli compile karma-test
karma start --single-run --reporters junit,dots
