(ns botanalyticsapi.api-test
  (:require [clojure.test :refer :all]
            [clj-http.client :as http]
            [botanalyticsapi.api :refer :all]
            [clj-http-mock.core :as mock]
            [clj-http-mock.response :refer :all]))

(def post-botanalytics-url "https://botanalytics.co/api/v1/messages/generic/")

(deftest test-post-message
  (mock/with-mock-routes-in-isolation
    [(mock/route :post post-botanalytics-url mock/any)
     (ok-response "ok")]
    (let [reply (post-message "token" true {:user {:id 1 :name "name"}} {:message {:timestamp 1 :text "text"}})]
      (is (= reply "ok")))))