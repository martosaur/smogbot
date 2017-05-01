(ns airlyapi.api-test
  (:require [clojure.test :refer :all]
            [clj-http.client :as http]
            [airlyapi.api :refer :all]
            [clj-http-mock.core :as mock]
            [clj-http-mock.response :refer :all]))

(def get-measurements-url "https://airapi.airly.eu/v1/mapPoint/measurements")

(def valid-response {:currentMeasurements
                     {:airQualityIndex 12.69299838441799
                      :pm1 6.356808510638296
                      :pm25 7.245459021276594
                      :pm10 9.33574333841131
                      :pressure 101875.58872340423
                      :pollutionLevel 1}})

(def error-response {:errors [{:code 0
                               :message "string"}]})

(deftest test-get-map-point-measurements
  (testing "valid response"
    (mock/with-mock-routes-in-isolation
      [(mock/route :get get-measurements-url mock/any)
       (ok-response (cheshire.core/generate-string valid-response))]
      (let [reply (get-map-point-measurements "token" 1 0)]
        (is (= reply valid-response)))))

  (testing "not found response"
    (mock/with-mock-routes-in-isolation
      [(mock/route :get get-measurements-url mock/any)
       (not-found-response)]
      (is (thrown? clojure.lang.ExceptionInfo (get-map-point-measurements "token" 666 666)))))

  (testing "error response"
    (mock/with-mock-routes-in-isolation
      [(mock/route :get get-measurements-url mock/any)
       (constantly {:status 500 :headers {} :body (cheshire.core/generate-string error-response)})]
      (is (thrown? clojure.lang.ExceptionInfo (get-map-point-measurements "token" 1 1))))))