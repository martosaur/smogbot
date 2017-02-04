(ns smogbot.keyboards)

(def default-keyboard
  {
   :keyboard [[{:text "please gimme GPS"
                :request_location true}]]
   :resize_keyboard true
   :selective true
   })