(ns scripts.peek-at-failed-account-alert-settings
  (:require [tools.primex.aws :as paws]
            [tools.primex.customer-service :as cs]))

(defn -main
  [& args]
  (let [[prefix environment queue-name] args
        account-ids (atom #{})]
    (dotimes [n 10]
      (paws/peek-and-save-failed-account-ids prefix environment queue-name account-ids))
    (println (cs/list-accts-not-found environment @account-ids))))
