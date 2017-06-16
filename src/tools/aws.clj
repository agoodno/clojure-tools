(ns tools.aws
  (:require [clojure.pprint :as pprint]
            [amazonica.aws.ec2 :as ec2]
            ))

(defn aws-cred [environment]
  "Establishes AWS credentials from .aws/config"
  (let [aws-region (if (= environment "development") "us-west-2" "us-east-1")]
    {:profile "default" :endpoint aws-region}))

(defn instance-ip-address [environment service-name filters]
  "Displays the current IP address of a service backed by an EC2 instance"
  (comment ex. (tools.core/instance-ip-address "development" "AlertService" []))
  (comment ex. (tools.core/instance-ip-address "integration" "AlertService" []))
  (let
      [cred (aws-cred environment)
       described (ec2/describe-instances
                  cred
                  :filters filters)]
    (->
     (get-in described [:reservations])
     (get-in [0 :instances])
     (get-in [0 :network-interfaces])
     (get-in [0 :private-ip-addresses])
     (get-in [0 :private-ip-address]))))
