(ns smogbot.updates
  (require [morse.polling :as p]
           [smogbot.bot :as bot]
           [smogbot.helpers :refer [bot-token]]))

(defn start
  []
  (p/start bot-token bot/updates-worker))

(defn stop
  [channel]
  (p/stop channel))
