(ns tools.primex.customer-service
  (:require [tools.db :as db]
            [clojure.java.jdbc :as jdbc]))

(def dev-db {:dbtype "postgresql"
             :dbname "customerservice_development"
             :host "localhost"
             :user "agoodnough"
             :password "testing"})

(defn list-acct-basic-info [environment]
  (let [accts (jdbc/query dev-db ["SELECT * FROM \"accounts\""])]
    (map #(select-keys % [:id :name]) accts)))
