(ns smogbot.helpers)

(def bot-token (if-let [token (System/getenv "BOT_TOKEN")]
                 token
                 (System/getProperty "BOT_TOKEN")))

(def api-token (if-let [token (System/getenv "API_TOKEN")]
                 token
                 (System/getProperty "API_TOKEN")))