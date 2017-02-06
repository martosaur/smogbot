(ns airlyapi.api
  (:require [clj-http.client :as http]))

(def base-url "https://airapi.airly.eu")

(defn get-map-point-measurements
  "Get measurements for mapPoint"
  [api-key latitude longitude]
  (let [url (str base-url "/v1/mapPoint/measurements")
        resp (http/get url {:accept :json
                            :as :json
                            :headers {:apikey api-key :user-agent "curl"}
                            :query-params {:latitude latitude :longitude longitude}})]
    (-> resp :body)))