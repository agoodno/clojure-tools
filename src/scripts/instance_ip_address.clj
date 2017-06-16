(ns scripts.instance-ip-address
  (:require [tools.primex.aws :as paws]))

(defn -main
  [& args]
  (let [[environment service-name] args]
  (println (paws/primex-instance-ip-address environment service-name))))
