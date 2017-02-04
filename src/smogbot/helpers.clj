(ns smogbot.helpers)

(def bot-token (if-let [token (System/getenv "BOT_TOKEN")]
                 token
                 (System/getProperty "BOT_TOKEN")))