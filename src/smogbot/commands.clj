(ns smogbot.commands
  (require [morse.api :as api]
           [smogbot.helpers :refer [bot-token api-token]]
           [smogbot.keyboards :as keyboards]
           [airlyapi.api :as air-api]))

(def messages
  {:start (slurp (.getFile (clojure.java.io/resource "start.md")))
   :help (slurp (.getFile (clojure.java.io/resource "help.md")))
   :legend (slurp (.getFile (clojure.java.io/resource "legend.md")))})

(defn reply-with-text
  "Responds with text"
  [{{chat-id :id} :chat message-id :message_id} command]
  (api/send-text bot-token chat-id
                 {:reply_markup keyboards/default-keyboard
                  :reply_to_message_id message-id
                  :parse_mode "Markdown"}
                 (command messages)))

(defn get-map-point-conditions
  "Get conditions of a map point"
  [{latitude :latitude longitude :longitude}]
  (let [result (air-api/get-map-point-measurements api-token latitude longitude)]
    (-> result :currentMeasurements)))

(def pollution-level-message {0 "No current data"
                              1 (rand-nth ["Wonderful!" "Great air here today!"])
                              2 (rand-nth ["Air quality is fine." "Acceptable."])
                              3 (rand-nth ["Well… It’s been better." "Above daily WHO recommendations."])
                              4 (rand-nth ["Poor air quality!" "Bad air quality!"])
                              5 (rand-nth ["Air pollution is way over the limit." "ExtremAIRly poor."])
                              6 (rand-nth ["AIRmageddon!" "DisAIRster!"])})

(defn measurements->text
  "Returns a human-readable representation of currentMeasurements"
  [{caqi :airQualityIndex pm25 :pm25 pm10 :pm10 pressure :pressure humidity :humidity temp :temperature pollution-level :pollutionLevel :as data}]
  (if data
    (str
      "*CAQI:* " (when caqi (int caqi)) " *" (pollution-level-message pollution-level) "*\n"
      "*PM25:* " (when pm25 (format "%.2f" pm25)) " μg/m3\n"
      "*PM10:* " (when pm10 (format "%.2f" pm10)) " μg/m3\n"
      "*Pressure:* " (when pressure(int (/ pressure 100))) " hPa\n"
      "*Humidity:* " (when humidity (int humidity)) "%\n"
      "*Temperature: *" (when temp (int temp)) "°C\n"
      "More info at [airly.eu](https://airly.eu/")
    (str
      "Sorry, no data for your location available")))

(defn reply-on-message
  "Responds on a message"
  [message]
  (if-let [location (:location message)]
    (api/send-text bot-token (:id (:chat message))
                   {:reply_to_message_id (:message_id message)
                    :disable_web_page_preview true
                    :parse_mode "Markdown"}
                   (-> location
                       get-map-point-conditions
                       measurements->text))
    (reply-with-text message :help)))