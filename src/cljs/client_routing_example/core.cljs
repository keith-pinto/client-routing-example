(ns client-routing-example.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [bidi.bidi :as bidi]
            [client-routing-example.routes :as routes]
            [client-routing-example.states :as states]
            [client-routing-example.components :as components]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [cljs.core.async :refer [chan put! <!]]
            )
  (:import goog.History))

(def hist (History.))

;inital state
(def app-state
  (atom (merge {:dataChan (chan 1)} states/default-state)))

(om/root
 components/application
 app-state
 {:target (. js/document (getElementById "app"))})

;;Routing related code below
(defn default-if-empty
  [value default-value]
  (if (empty? value) default-value value))

;;Ideally this function would fetch state from the server,
;;currently fetching static states mentioned in states.cljs
(defn fetch-state [k]
  (states/data k))

(defn render-page [event]
  (try
    (let [route (default-if-empty (.-token event) "/")
          route-handler ((bidi/match-route routes/routes (str "/#" route)) :handler)
          current-state (fetch-state route-handler)]
      (.log js/console "current route: " route)
    (.log js/console "fetched state: " current-state)
    (if (= (current-state :unauthorized) true) 
      (do
        (.log js/console "redirect to login")
        (.setToken hist "/login")) 
      (put! (@app-state :dataChan) current-state)))
    (catch js/Error e
      (.log js/console (.-stack e))
      (.setToken hist "/error"))))

;; https://github.com/gf3/secretary#example-with-googhistory.
;; Listen on the Navigate event, to capture change in URL
(do
  (goog.events/listen hist EventType/NAVIGATE render-page)
  (doto hist (.setEnabled true)))
