(ns botanalyticsapi.api
  (:require [clj-http.client :as http]))

(def base-url "https://botanalytics.co")

(defn post-message
  "Post message to bot analytics service"
  [api-key is-sender-bot user message]
  (let [url (str base-url "/api/v1/messages/generic/")
        body {:is_sender_bot is-sender-bot
              :user user
              :message message}
        auth-header (str "Token " api-key)
        resp (http/post url {:form-params      body
                             :content-type     :json
                             :headers     {:Authorization auth-header}
                             :throw-exceptions false})]
    (-> resp :body)))
