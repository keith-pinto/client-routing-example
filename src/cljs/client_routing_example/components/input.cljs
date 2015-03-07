(ns client-routing-example.components.input)

(defn text-input  [app owner  {:keys  [id name identifier type placeholder]}]
    "om component for text input"
    (reify
          om/IWillReceiveProps
          (will-receive-props  [_ next-props]
                                    (when  (empty?  (-> next-props :errors identifier))
                                                                (if  (empty?  (identifier next-props))
                                                                                              (om/set-state! owner :text "")
                                                                                              (om/set-state! owner :text  (identifier next-props)))))
          om/IRenderState
          (render-state  [this  {:keys  [formdata text] :as state}]
                        (dom/input #js  {:type type
                                                                     :id id
                                                                     :className "form-control"
                                                                     :name name
                                                                     :value text
                                                                     :placeholder placeholder
                                                                     :onChange #(util/handle-change % owner state)
                                                                     :onBlur #(put! formdata  {:name identifier
                                                                                                                                                     :text text})}))))

