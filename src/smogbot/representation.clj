(ns smogbot.representation)

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

(defn pm25-message
  "Returns representation for pm25"
  [pm25]
  (str
    "*PM25:* " (when pm25 (format "%.2f" pm25)) " μg/m3"))

(defn pm10-message
  "Returns representation for pm10"
  [pm10]
  (str
    "*PM10:* " (when pm10 (format "%.2f" pm10)) " μg/m3"))

(defn pressure-message
  "Returns representation for pressure"
  [pressure]
  (str
    "*Pressure:* " (when pressure(int (/ pressure 100))) " hPa"))

(defn humidity-message
  "Returns representation for humidity"
  [humidity]
  (str
    "*Humidity:* " (when humidity (int humidity)) "%"))

(defn temperature-message
  "Returns representation for temperature"
  [temp]
  (str
    "*Temperature: *" (when temp (int temp)) "°C"))

(defn measurements->text
  "Returns a human-readable representation of currentMeasurements"
  [{caqi :airQualityIndex pm25 :pm25 pm10 :pm10 pressure :pressure humidity :humidity temp :temperature pollution-level :pollutionLevel :as data}]
  (if (not-empty data)
    (clojure.string/join "\n"
                         (list
                           (caqi-message caqi pollution-level)
                           (pm25-message pm25)
                           (pm10-message pm10)
                           (pressure-message pressure)
                           (humidity-message humidity)
                           (temperature-message temp)
                           "More info at [airly.eu](https://airly.eu/)"))
    "Sorry, no data available for your location"))