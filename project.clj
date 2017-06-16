(defproject tools "0.1.0-SNAPSHOT"
  :description "Clojure Tools"
  :url "https://agoodno.com/"
  :license {:name "GNU Public License"
            :url "https://www.gnu.org/licenses/gpl.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "2.0.0"]
                 [cheshire "5.7.1"]
                 [clj-time "0.13.0"]
                 [amazonica "0.3.99"]
                 [org.clojure/java.jdbc "0.7.0-alpha3"]
                 [org.postgresql/postgresql "42.1.1"]]
  :profiles {:instance-ip-address
             {:main scripts.instance-ip-address}
             :list-accounts
             {:main scripts.list-accounts}
             :peek-at-failed-account-alert-settings
             {:main scripts.peek-at-failed-account-alert-settings}}
  :aliases {"instance-ip-address"
            ["with-profile" "instance-ip-address" "run"]
            "list-accounts"
            ["with-profile" "list-accounts" "run"]
            "peek-at-failed-account-alert-settings"
            ["with-profile" "peek-at-failed-account-alert-settings" "run"]})
