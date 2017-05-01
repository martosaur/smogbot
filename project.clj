(defproject smogbot "1.0.1-SNAPSHOT"
  :description "smog bot"
  :url "https://t.me/krakowsmogbot"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [morse "0.2.4"]
                 [clj-http "2.3.0"]]
  :plugins [[lein-ring "0.9.7"]
            [lein-beanstalk "0.2.7"]]
  :ring {:init smogbot.updates/start
         :handler smogbot.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]
                        [clj-http-mock "0.4.0"]]}}
  :aws {:beanstalk {:environments [{:name "production"
                                    :cname-prefix "smogbot-production"
                                    :env {"BOT_TOKEN" ~(System/getenv "BOT_TOKEN")
                                          "API_TOKEN" ~(System/getenv "API_TOKEN")}}]
                    :region "eu-west-1"}})
