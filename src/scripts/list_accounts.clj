(ns scripts.list-accounts
  (:require [tools.primex.customer-service :as cs]))

(defn -main
  [& args]
  (let [[environment] args]
    (println (cs/list-acct-basic-info environment))))
