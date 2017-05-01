(ns smogbot.representation-test
  (:require [clojure.test :refer :all]
            [smogbot.representation :refer :all]))

(deftest test-pollution-level-message
  (is (string? (pollution-level-message 0)))
  (is (string? (pollution-level-message 1)))
  (is (string? (pollution-level-message 2)))
  (is (string? (pollution-level-message 3)))
  (is (string? (pollution-level-message 4)))
  (is (string? (pollution-level-message 5)))
  (is (string? (pollution-level-message 6)))
  (is (nil? (pollution-level-message 7))))

(deftest test-caqi-message
  (testing "no message"
    (is (= (caqi-message nil nil) "*CAQI:*  **")))
  (testing "valid message"
    (is (= (caqi-message 1 0) "*CAQI:* 1 *No current data*")))
  (testing "float caqi"
    (is (= (caqi-message 66.66 0) "*CAQI:* 66 *No current data*"))))

(deftest test-percentage-of-norm
  (testing "float numbers"
    (is (= (percentage-of-norm 33.3 99.9) "33%")))
  (testing "int numbers"
    (is (= (percentage-of-norm 45 1000) "5%")))
  (testing "nils"
    (is (= (percentage-of-norm nil nil) "%"))))

(deftest test-pm25-message
  (is (= (pm25-message 12.5) "*PM25:* 50%"))
  (is (= (pm25-message nil) "*PM25:* %")))

(deftest test-pm10-message
  (is (= (pm10-message 12.5) "*PM10:* 25%"))
  (is (= (pm10-message nil) "*PM10:* %")))

(deftest test-temperature-message
  (is (= (temperature-message 12) "*Temperature: *12°C"))
  (is (= (temperature-message 66.6) "*Temperature: *66°C"))
  (is (= (temperature-message -100) "*Temperature: *-100°C"))
  (is (= (temperature-message nil) "*Temperature: *°C")))

(deftest test-measurements->text
  (testing "valid data"
    (let [measurements {:airQualityIndex 55.5
                        :pm25 12.5
                        :pm10 12.5
                        :temperature 36.6
                        :pollutionLevel 0}
          message (measurements->text measurements)]
      (is (= message "*CAQI:* 55 *No current data*\n*PM25:* 50%\n*PM10:* 25%\n*Temperature: *36°C"))))
  (testing "empty data"
    (let [measurements {}
          message (measurements->text measurements)]
      (is (= message "Sorry, no data available for your location"))))
  (testing "bullshit data"
    (let [measurements {:bullshit true}
          message (measurements->text measurements)]
      (is (= message "Sorry, no data available for your location"))))
  (testing "incomplete data"
    (let [measurements {:airQualityIndex nil
                        :temperature 11}
          message (measurements->text measurements)]
      (is (= message "*CAQI:*  **\n*PM25:* %\n*PM10:* %\n*Temperature: *11°C")))))