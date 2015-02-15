(ns client-routing-example.components
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [sablono.core :as html :refer-macros [html]]
            [client-routing-example.states :as states]
            [cljs.core.async :refer [chan <!]]
            )
  (:require-macros  [cljs.core.async.macros :refer  [go-loop]]))

(defn splash [app owner {:keys [text]}]
  (reify
    om/IRender
    (render [_]
      (html [:h1 nil text]))))

(defn component-map [app]
  {
   :a [splash app {:opts {:text "a"}}]
   :b [splash app {:opts {:text "b"}}]
   :c [splash app {:opts {:text "c"}}]
   :splash-main [splash app {:opts {:text "Splash main"}}]
   :splash-left [splash app {:opts {:text "Splash left"}}]
   :splash-top [splash app {:opts {:text "Splash top"}}]
   :splash-right [splash app {:opts {:text "Splash right"}}]
   })

(defn build-component [app kw]
  (let [dm (component-map app)]
    (->> kw (get dm) (apply om/build))))

;main component must have a core.async channel within the
;state which can be accessed via :dataChan
(defn application
  "Component that represents our application. Maintains session state.
  Selects views based on session state."
  [{{:keys [top left main]} :display :as app} owner]
  (reify
    om/IWillMount
    (will-mount [_]
      (go-loop [new-state (->
                            app
                            :dataChan
                            <!)]
               (.log js/console "received state within component!")
               (om/update! app [:display] (new-state :display))
               (recur (-> app :dataChan <!))
               ))
    om/IRender
    (render [_]
      (html [:div {}
             [:h1 {} (str (zipmap (keys app) (vals app)))]
             [:div {:style {:text-align "center"
                            :padding "5px"}}
              (build-component app top)]
             [:div  {:style {:line-height "30px"
                             :background-color "#eeeeee"
                             :height "1000px"
                             :width "400px"
                             :float "left"
                             :padding "5px"}}
              (build-component app left)]
             [:div {:style {:width "350px"
                            :float "left"
                            :padding "10px"
                            :align "center"}}
               (build-component app main)]]))))
