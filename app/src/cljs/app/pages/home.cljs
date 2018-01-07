(ns app.pages.home
  (:require  [reagent.core :as r]
             [re-frame.core :as rf]
             [app.events]
             [cljsjs.material-ui]
             [cljs-react-material-ui.core :refer [get-mui-theme color]]
             [cljs-react-material-ui.reagent :as ui]
             [cljs-react-material-ui.icons :as ic]
             [stylefy.core :as stylefy]
             [app.global-styles :as global-styles]))

(defn home-page []

  [:div {:style {:display "flex" :flex-wrap "nowrap"
                 :justify-content "center"}}

   [:div {:style {:display "flex" :flex "0 1 40%"}}
    [ui/card
     [ui/card-media [:img {:src
                           ;; (str js/context "/img/file-name-in-resources-public-img.jpg")
                           "https://dummyimage.com/1600x900/1568c0/ffffff.jpg&text=A+nice+picture+of+me"
                           :alt "a decent picture of myself"}]]]]

   [:div {:style {:display "flex" :flex "0 0 auto"}}
    [ui/paper {:style {:padding "1em"}}
     [:div [:p "this is some bio this content has a bunch more stuff"]]]]
   ])
