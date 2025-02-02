(ns undead.client.main
  (:require [dumdom.core :as d]
            [chord.client :as chord]
            [cljs.core.async :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(d/defcomponent Page [props]
  [:div.page
   [:div.surface
    [:div.skyline
     (for [i (range 16)]
       [:div.building {:class (str "building-" i)}])]]])


(defn render []
  (d/render (Page {}) (js/document.getElementById "main")))

(defn start []
  (go
    (let [{:keys [error ws-channel]} (<! (chord/ws-ch "ws://localhost:8001/ws"))]
      (when error
        (throw error))
      (println (<! ws-channel)))))