(ns smogbot.helpers)

(def bot-token (if-let [token (System/getenv "BOT_TOKEN")]
                 token
                 (System/getProperty "BOT_TOKEN")))

(def api-token (if-let [token (System/getenv "API_TOKEN")]
                 token
                 (System/getProperty "API_TOKEN")))

(def city-center-location {:latitude 50.0611127
                           :longitude 19.9379205})

(def pm10-norm 50)

(def pm25-norm 25)