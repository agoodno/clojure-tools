(ns tools.primex.customer-service
  (:require [tools.db :as db]
            [clojure.java.jdbc :as jdbc]
            [clojure.set :as set]
            [clojure.test :as test]))

(def dev-db {:dbtype "postgresql"
             :dbname "customerservice_development"
             :host "localhost"
             :user "agoodnough"
             :password "testing"})

(defn list-acct-basic-info [environment]
  (let [accts (jdbc/query dev-db ["SELECT * FROM \"accounts\""])]
    (map #(select-keys % [:id :name]) accts)))

(defn list-accts-not-found [environment unique_acct_ids]
  "Compares a set of unique account ids to what is in the database and
  prints out account ids that are missing in the database."
  (let [accts_delim (apply str (interpose \, unique_acct_ids))
        db_acct_ids (map :id (jdbc/query dev-db [(str
                                                  "SELECT id FROM accounts WHERE id IN ("
                                                  accts_delim
                                                  ")")]))]
    (set/difference unique_acct_ids (set/intersection unique_acct_ids (set db_acct_ids)))))
