(ns client-routing-example.components.bootstrap.layouts
  (:require [om.core :as om :include-macros true]
            ;[om.dom :as dom :include-macros true]
            [sablono.core :as html :refer-macros [html]]
            ))

#_(defn two-column [app owner]
  (reify
    om/IRender
    (render [_]
      (d/div
        {:class "page"}
        (g/grid {}
                (g/row
                  (g/col {:xs 8 :md 8 :xs-offset 2 :md-offset 2}
                         (g/row
                           (g/col {:xs 12 :md 12}
                                  (d/code  {} "navbar"))
                           (g/col {:xs 3 :md 3}
                                  (d/code  {} "sidebar"))
                           (g/col {:xs 9 :md 9}
                                  (d/code  {} "content"))))))))))


