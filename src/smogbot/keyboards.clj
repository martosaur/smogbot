(ns smogbot.keyboards)

(def check-city-center-text "Check city center")

(def default-keyboard
  {
   :keyboard [[{:text "Check my location"
                :request_location true}]
              [{:text check-city-center-text}]]
   :resize_keyboard true
   })