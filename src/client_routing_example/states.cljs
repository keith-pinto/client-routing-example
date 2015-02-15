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

(def cab-state (assoc default-state 
                    :display {:top :c
                              :left :a
                              :main :b}))

(def data {:default default-state
            :abc abc-state
            :bac bac-state
            :cab cab-state})
