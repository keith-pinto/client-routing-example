(ns client-routing-example.components.login
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [sablono.core :as html :refer-macros [html]]
            [ajax.core :refer [GET POST]]
            ))

(defn form [app owner]
  (reify
    om/IRender
    (render [_]
      (html [:div
             [:input
              {:name "username"
               :type "text"}]
             [:input
              {:name "password"
               :type "password"}]
             [:a
              {:href "#"
               :on-click (fn [e]
                           (.preventDefault e)
                           (.log js/console "something happened")
                           (POST "/login"
                             :params {:username "friend"
                                     :password "clojure"}
                             :handler #(.log js/console "success")
                             :error-handler #(.log js/console "failed")))
               }
              "Login"]
             ]))))


