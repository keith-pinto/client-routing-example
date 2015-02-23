(ns client-routing-example.core
  (:require [client-routing-example.users :as users :refer  (users)]
            [ring.adapter.jetty :as r]
            [ring.util.response :as resp]
            ;[ring.middleware.reload :as r-dev]
            [compojure.core :as compojure :refer  (GET POST ANY defroutes context)]
            (compojure [handler :as handler]
                       [route :as route])
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])))

(defroutes my-routes
  (GET "/" [] (str "home page"))
  (GET "/login" [] (str "login page"))
  (friend/logout  (ANY "/logout" request  (resp/redirect "/")))
  (context "/user" []
           (GET "/home" [] 
                (friend/authorize #{::users/user} "You logged in!"))))

(def app (handler/site my-routes))

(def secured-app
  (-> app
      (friend/authenticate {:allow-anon? true
                            :login-uri "/login"
                            :default-landing-uri "/"
                            :unauthorized-handler #(-> (str "You do not have sufficient privileges to access" (:uri %)) 
                                                       resp/response
                                                       (resp/status 401))
                            :credential-fn #(creds/bcrypt-credential-fn @users %)
                            :workflows  [(workflows/interactive-form)]})
            ; ...required Ring middlewares ...
      ))

(defn -main []
  (r/run-jetty secured-app {:port 3000}))

#_(def app
    (r-dev/wrap-reload #'handler '(client-routing-example.core)))

#_(defn boot  []
    (r/run-jetty #'app  {:port 8080}))
