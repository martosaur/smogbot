(ns smogbot.helpers
  (:require [botanalyticsapi.api :as botanalytics]))

(def bot-token (if-let [token (System/getenv "BOT_TOKEN")]
                 token
                 (System/getProperty "BOT_TOKEN")))

(def api-token (if-let [token (System/getenv "API_TOKEN")]
                 token
                 (System/getProperty "API_TOKEN")))

(def analytics-token (if-let [token (System/getenv "ANALYTICS_TOKEN")]
                    token
                    (System/getProperty "ANALYTICS_TOKEN")))

(def city-center-location {:latitude 50.0611127
                           :longitude 19.9379205})

(def pm10-norm 50)

(def pm25-norm 25)

(def post-message-to-analytics (partial botanalytics/post-message analytics-token))

(def post-incoming-message-to-analytics (partial post-message-to-analytics false))

(def post-outgoing-message-to-analytics (partial post-message-to-analytics true))
