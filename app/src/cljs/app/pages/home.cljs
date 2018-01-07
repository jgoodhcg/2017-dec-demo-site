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

  [:div {:style {:display "flex" :flex-wrap "wrap" :align-content "stretch"}}
   [ui/paper
    [:div [:p "this is some picture"]]]
   [ui/paper
    [:div [:p "this is some bio this content has a bunch more stuff"]]]])
