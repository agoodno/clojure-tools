(ns tools.core
  (:use [amazonica.aws.ec2]))

(defn instance-ip-address [environment service-name]
  "Displays the current IP address of a service backed by an EC2 instance"
  (let
      [aws-region (if (= environment "development") "us-west-2" "us-east-1")
       cred {:profile "default" :endpoint aws-region}
       described (describe-instances
                  cred
                  :filters [{:name "tag:StackName" :values [environment]}
                            {:name "tag:PrimexApplication" :values [service-name]}])]
    (->
     (get-in described [:reservations])
     (get-in [0 :instances])
     (get-in [0 :network-interfaces])
     (get-in [0 :private-ip-addresses])
     (get-in [0 :private-ip-address]))))
