(ns scripts.peek-at-failed-account-alert-settings
  (:require [tools.primex.aws :as paws]))

(defn -main
  [& args]
  (let [[prefix environment queue-name] args
        account-ids (atom #{})]
    (do
      (paws/peek-and-save-failed-account-ids prefix environment queue-name account-ids)
      (println @account-ids))))
