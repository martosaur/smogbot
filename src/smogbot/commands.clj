(ns smogbot.commands
  (require [morse.api :as api]
           [smogbot.helpers :refer [bot-token]]
           [smogbot.keyboards :as keyboards]))

(def messages
  {:start (slurp "resources/start.md")
   :help (slurp "resources/help.md")})

(defn reply-with-text
  "Responds with text"
  [{{chat-id :id} :chat message-id :message_id} command]
  (api/send-text bot-token chat-id
                 {:reply_markup keyboards/default-keyboard
                  :reply_to_message_id message-id
                  :parse_mode "Markdown"}
                 (command messages)))

(defn reply-on-message
  "Responds on a message"
  [message]
  (if-let [location (:location message)]
    (api/send-text bot-token (:id (:chat message)) (str "your coordinates are:" location))
    (reply-with-text message :help)))