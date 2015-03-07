(defproject client-routing-example "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2755"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [figwheel "0.2.1-SNAPSHOT"]
                 ;memoize need because of figwheel issue
                 ;https://github.com/bhauman/lein-figwheel/issues/32
                 [org.clojure/core.memoize "0.5.6"]

                 [ring/ring-core "1.3.2"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [compojure "1.3.2"]
                 [com.cemerick/friend "0.2.1"]

                 [sablono "0.3.3"]
                 [bidi "1.18.0" :exclusions  [org.clojure/clojure]]
                 [org.omcljs/om "0.8.8"]
                 ;[racehub/om-bootstrap "0.4.1" :exclusions  [org.omcljs/om]]
                 [cljs-ajax "0.3.10"]

                 ;WebJars from http://www.webjars.org/
                 [org.webjars/bootstrap "3.3.2-1"]
                 ]
  :dev-dependencies [[ring/ring-devel "1.3.2"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-ring "0.8.11"]
            ;[lein-bower "0.5.1"]
            [lein-figwheel "0.2.1-SNAPSHOT"]
            ]

  :source-paths ["src/clj" "target/classes"]

  :main client-routing-example.core
  :ring {:handler client-routing-example.core/secured-app}

  :clean-targets ["resources/public/js/out/client_routing_example" "resources/public/js/out/client_routing_example.js"]

  :figwheel {
    :http-server-root "public"
    :server-port 3449
    ;:css-dirs ["resources/public/css"]
    :ring-handler client-routing-example.core/secured-app
  }

  :cljsbuild {
    :builds [{:id "client-routing-example"
              :source-paths ["src/cljs"]
              :compiler {
                :output-to "resources/public/js/out/client_routing_example.js"
                :output-dir "resources/public/js/out"
                :optimizations :none
                :cache-analysis true
                :source-map true}}]})
