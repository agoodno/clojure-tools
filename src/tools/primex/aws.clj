(ns tools.primex.aws
  (:require [tools.aws :as aws]
            [amazonica.aws.sqs :as sqs]
            [cheshire.core :as json]))

(defn primex-queue-name [prefix environment queue-name]
  (comment ex. (primex-queue-name "agoodnough" "development" "account_created_dead_letter"))
  (str prefix "_" environment "_" queue-name))

(defn primex-queue [queue-name]
  (comment ex. (primex-queue "agoodnough_development_account_created_dead_letter"))
  (sqs/find-queue queue-name))

(defn send-test-message [prefix environment queue-name msg]
  (comment ex. (tools.primex/send-test-message "agoodnough" "development" "account_created_dead_letter" { :account_id 1 }))
  (let [queue (primex-queue (primex-queue-name prefix environment queue-name))]
    (sqs/send-message queue (json/generate-string msg))))

(defn peek-and-save-failed-account-ids [prefix environment queue-name account-ids]
  (comment ex. (tools.primex/peek-and-check-account-alert-settings-exists "agoodnough" "development" "account_created_dead_letter"))
  (let [queue (primex-queue (primex-queue-name prefix environment queue-name))
        res (sqs/receive-message
             :queue-url queue
             :max-number-of-messages 10)
        msgs (res :messages)
        acct_infos (map #(json/parse-string (:body %) true) msgs)
        account_ids (map :account_id acct_infos)]
    (doseq [account_id account_ids]
      (swap! account-ids conj account_id))))

(defn list-account-ids [account_ids]
  (doseq [acct_id @account_ids] (println acct_id)))

(defn primex-instance-ip-address [environment service-name]
  "Displays the current IP address of a Primex service backed by an EC2 instance"
  (comment ex. (aws/instance-ip-address "development" "AlertService" []))
  (comment ex. (aws/instance-ip-address "integration" "AlertService" []))
  (let [primex-filters [{:name "tag:StackName" :values [environment]}
                        {:name "tag:PrimexApplication" :values [service-name]}]]
    (aws/instance-ip-address environment service-name primex-filters)))
