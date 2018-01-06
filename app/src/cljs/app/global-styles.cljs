(ns app.global-styles
  (:require [stylefy.core :as stylefy]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]))

(def primary-color "#4CAF50")
(def palette
  ;; palette: {
  ;;           primary1Color: cyan500,
  ;;           primary2Color: cyan700,
  ;;           primary3Color: grey400,
  ;;           accent1Color: pinkA200,
  ;;           accent2Color: grey100,
  ;;           accent3Color: grey500,
  ;;           textColor: darkBlack,
  ;;           alternateTextColor: white,
  ;;           canvasColor: white,
  ;;           borderColor: grey300,
  ;;           disabledColor: fade(darkBlack, 0.3),
  ;;           pickerHeaderColor: cyan500,
  ;;           clockCircleColor: fade(darkBlack, 0.07),
  ;;           shadowColor: fullBlack,
  ;;           },
 {:text-color (color :darkBlack)
  :primary1Color (color :blue800)
  :primary2Color (color :blue900)
  :alternateTextColor (color :grey50)})

(def phone-width 571)

(def style-responsive-show-on-phone {:display "none"
                                     ::stylefy/media {{:max-width (str phone-width "px")}
                                                             {:display "flex"}}})

(def style-responsive-hide-on-phone {:display "none"
                                     ::stylefy/media {{:min-width (str (+ 1 phone-width) "px")}
                                                      {:display "flex"}}})

