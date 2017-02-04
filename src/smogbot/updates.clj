(ns smogbot.updates
  (require [morse.polling :as p]
           [smogbot.bot :as bot]
           [smogbot.helpers :refer [bot-token]]))

(defn start
  []
  (p/start bot-token bot/bot-api))

(defn stop
  [channel]
  (p/stop channel))
