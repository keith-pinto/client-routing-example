(ns client-routing-example.routes)

(def routes ["/#" {"/"  :default
                   "/abc" :abc
                   "/bac" :bac
                   "/cab" :cab
                   "/login" :login
                   "/error" :error
                   }])
