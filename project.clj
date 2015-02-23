(defproject client-routing-example "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2755"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [sablono "0.3.3"]
                 [bidi "1.18.0"]
                 [org.omcljs/om "0.8.8"]

                 [ring/ring-core "1.3.2"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [compojure "1.3.2"]
                 [com.cemerick/friend "0.2.1"]
                 ]
  :dev-dependencies [[ring/ring-devel "1.3.2"]]

  :plugins [[lein-cljsbuild "1.0.4"]]

  :source-paths ["src/clj" "target/classes"]

  :main client-routing-example.core
  :ring {:handler core/site}

  :clean-targets ["out/client_routing_example" "out/client_routing_example.js"]

  :cljsbuild {
    :builds [{:id "client-routing-example"
              :source-paths ["src/cljs"]
              :compiler {
                :output-to "out/client_routing_example.js"
                :output-dir "out"
                :optimizations :none
                :cache-analysis true
                :source-map true}}]})
