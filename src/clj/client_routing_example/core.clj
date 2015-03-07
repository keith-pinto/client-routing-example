(ns client-routing-example.core
  (:require [client-routing-example.users :as users :refer  (users)]
            [ring.adapter.jetty :as r]
            [ring.util.response :as response]
            [ring.middleware.resource :as resource :refer (wrap-resource)]
            [compojure.core :as compojure :refer  (GET POST ANY defroutes context)]
            (compojure [handler :as handler]
                       [route :as route])
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds]))
  )

(defroutes my-routes
  (GET "/" [] (response/file-response "index.html" {:root "resources/html"}))
  (GET "/login" [] (str "login page"))
  (friend/logout  (ANY "/logout" request  (response/redirect "/")))
  (context "/user" []
           (GET "/home" [] 
                (friend/authorize #{::users/user} "You logged in!")))
  (route/files "/resources" {:root "resources/public"})
  (route/not-found "Not Found")
  )

(def secured-app
  (-> my-routes
      (wrap-resource "/META-INF/resources")
      (friend/authenticate {:allow-anon? true
                            :login-uri "/login"
                            :default-landing-uri "/user/home"
                            :unauthorized-handler #(-> (str "You do not have sufficient privileges to access" (:uri %)) 
                                                       response/response
                                                       (response/status 401))
                            :credential-fn #(creds/bcrypt-credential-fn @users %)
                            :workflows  [(workflows/interactive-form)]})
      (handler/site)
      ))

(defn -main []
  (r/run-jetty secured-app {:port 3000}))
