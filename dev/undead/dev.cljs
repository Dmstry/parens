(ns ^:figwheel-hooks undead.dev
    (:require [undead.client.main :as main]))

(defn ^:after-load render []
  (println "Hello, zombies!"))