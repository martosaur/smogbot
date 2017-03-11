(ns smogbot.commands
  (require [morse.api :as api]
           [smogbot.helpers :refer [bot-token api-token city-center-location]]
           [smogbot.keyboards :as keyboards]
           [smogbot.representation :as representation]
           [airlyapi.api :as air-api]))

(def messages
  {:start (slurp (.getFile (clojure.java.io/resource "start.md")))
   :help (slurp (.getFile (clojure.java.io/resource "help.md")))
   :legend (slurp (.getFile (clojure.java.io/resource "legend.md")))})

(defn get-map-point-conditions
  "Get conditions of a map point"
  [{latitude :latitude longitude :longitude}]
  (let [result (air-api/get-map-point-measurements api-token latitude longitude)]
    (-> result :currentMeasurements)))

(defn reply-with-text
  "Responds with text"
  [{{chat-id :id} :chat message-id :message_id} command]
  (api/send-text bot-token chat-id
                 {:reply_markup keyboards/default-keyboard
                  :reply_to_message_id message-id
                  :parse_mode "Markdown"}
                 (command messages)))

(defn reply-with-data
  "Responds with the air conditions data"
  [{{chat-id :id} :chat message-id :message_id} location]
  (api/send-text bot-token chat-id
                 {:reply_to_message_id message-id
                  :disable_web_page_preview true
                  :parse_mode "Markdown"}
                 (-> location
                     get-map-point-conditions
                     representation/measurements->text)))

(defn reply-on-message
  "Responds on a message"
  [message]
  (if-let [location (:location message)]
    (reply-with-data message location)
    (if (= (:text message) keyboards/check-city-center-text)
      (reply-with-data message city-center-location)
      (reply-with-text message :help))))