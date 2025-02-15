(ns undead.client.main
  (:require [dumdom.core :as d]
            [chord.client :as chord]
            [cljs.core.async :refer [<!]]
            [clojure.core.match :refer [match]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce store (atom {}))

(d/defcomponent Page [{:keys [zombies]}]
  [:div.page
   [:div.surface
    [:div.skyline
     (for [i (range 16)]
       [:div.building {:class (str "building-" i)}])]
    [:div.zombies
     (for [zomie (vals zombies)]
       [:div.zombie-position
        [:div.zombie {:class (:kind zomie)}]])]]])


(defn render []
  (d/render (Page @store) (js/document.getElementById "main")))

(defn start []
  (add-watch store ::me (fn [_ _ _ _] (render)))
  (go
    (let [{:keys [error ws-channel]} (<! (chord/ws-ch "ws://localhost:8001/ws"))]
      (when error
        (throw error))
      (doseq [action (:message (<! ws-channel))]
        (prn action)
        (match action
          [:assoc-in path v] (swap! store assoc-in path v))))))