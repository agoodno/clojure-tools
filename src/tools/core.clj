(ns tools.core
  (:use [amazonica.aws.ec2]))

(defn instance-ip-address [environment service-name]
  "Displays the current IP address of a service backed by an EC2 instance"
  (let
      [aws-region (if (= environment "development") "us-west-2" "us-east-1")
       cred {:profile "default" :endpoint aws-region}
       described (describe-instances cred :filters [{:name "tag:StackName" :values [environment]} {:name "tag:PrimexApplication" :values [service-name]}])
       instance (first ((first (described :reservations)) :instances))
       ip (first ((first (instance :network-interfaces)) :private-ip-addresses))]
  (ip :private-ip-address)))
