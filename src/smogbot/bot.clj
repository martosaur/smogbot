(ns smogbot.bot
  (require [morse.handlers :refer :all]
           [morse.api :as api]
           [smogbot.commands :as commands]))

(defhandler bot-api
            (command "start" message (commands/reply-with-text message :start))
            (command "help" message (commands/reply-with-text message :help))
            (command "legend" message (commands/reply-with-text message :legend))
            (message message (commands/reply-on-message message)))

(defn updates-worker
  [data]
  (future (bot-api data)))