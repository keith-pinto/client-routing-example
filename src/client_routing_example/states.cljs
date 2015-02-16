(ns client-routing-example.states)

(def default-state {:session/state :open
                    :display {:top :splash-top
                              :left :splash-left
                              :main :splash-main}
                    :search/results []})

(def abc-state (assoc default-state 
                    :display {:top :a
                              :left :b
                              :main :c}))

(def bac-state (assoc default-state 
                    :display {:top :b
                              :left :a
                              :main :c}))

;;response for failed authenticated route
;;(because user hasn't logged in)
(def cab-state {:unauthorized true})

(def login-state (assoc default-state 
                    :display {:top :splash-top
                              :left :splash-left
                              :main :login}))

(def error-state (assoc default-state 
                    :display {:top :splash-top
                              :left :splash-left
                              :main :error}))

(def data {:default default-state
            :abc abc-state
            :bac bac-state
            :cab cab-state
            :login login-state
            :error error-state
           })
