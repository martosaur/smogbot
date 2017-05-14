(ns smogbot.bot
  (require [clojure.tools.logging :as log]
           [morse.handlers :refer :all]
           [morse.api :as api]
           [smogbot.commands :as commands]
           [smogbot.helpers :refer [post-incoming-message-to-analytics]]))

(defhandler bot-api
            (command "start" message (commands/reply-with-text message :start))
            (command "help" message (commands/reply-with-text message :help))
            (command "legend" message (commands/reply-with-text message :legend))
            (message message (commands/reply-on-message message)))

(defn track-incoming-message
  "Sends info about incoming message to botmetrics"
  [{{chat-id :id username :username} :chat text :text date :date location :location}]
  (let [user {:id chat-id
              :name username}
        message {:timestamp date
                 :text (or text (str location))}]
    (post-incoming-message-to-analytics user message)))

(defn updates-worker
  [data]
  (do
    (log/debug "Received update:" data)
    (future (bot-api data))
    (future (track-incoming-message (:message data)))))