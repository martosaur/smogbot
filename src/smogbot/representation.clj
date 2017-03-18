(ns smogbot.representation
  (require [smogbot.helpers :refer [pm10-norm pm25-norm]]))

(def pollution-messages-map {0 ["No current data"]
                             1 ["Wonderful!" "Great air here today!"]
                             2 ["Air quality is fine." "Acceptable."]
                             3 ["Well… It’s been better." "Above daily WHO recommendations."]
                             4 ["Poor air quality!" "Bad air quality!"]
                             5 ["Air pollution is way over the limit." "ExtremAIRly poor."]
                             6 ["AIRmageddon!" "DisAIRster!"]})

(defn pollution-level-message
  "Returns human-readable message describing CAQI level"
  [pollution-level]
  (rand-nth (get pollution-messages-map pollution-level )))

(defn caqi-message
  "Returns representation for CAQI"
  [caqi pollution-level]
  (str
    "*CAQI:* " (when caqi (int caqi)) " *" (pollution-level-message pollution-level) "*"))

(defn percentage-of-norm
  "Returns a graphical representation for % of norm value"
  [actual norm]
  (str (when actual (format "%.0f"(-> actual
                                       (/ norm)
                                       float
                                       (* 100)))) "%"))

(defn pm25-message
  "Returns representation for pm25"
  [pm25]
  (str
    "*PM25:* " (percentage-of-norm pm25 pm25-norm)))

(defn pm10-message
  "Returns representation for pm10"
  [pm10]
  (str
    "*PM10:* " (percentage-of-norm pm10 pm10-norm)))

(defn temperature-message
  "Returns representation for temperature"
  [temp]
  (str
    "*Temperature: *" (when temp (int temp)) "°C"))

(defn measurements->text
  "Returns a human-readable representation of currentMeasurements"
  [{caqi :airQualityIndex pm25 :pm25 pm10 :pm10 temp :temperature pollution-level :pollutionLevel :as data}]
  (if (not-empty data)
    (clojure.string/join "\n"
                         (list
                           (caqi-message caqi pollution-level)
                           (pm25-message pm25)
                           (pm10-message pm10)
                           (temperature-message temp)))
    "Sorry, no data available for your location"))