(ns app.pages.apps
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.events]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [stylefy.core :as stylefy]
            [app.global-styles :as global-styles]))


(def style-card-text {:display "flex"
                      :flex-wrap "wrap"
                      ::stylefy/media {{:max-width (str global-styles/phone-width "px")}
                                       {:flex-direction "column"}}})

(def apps [{:avatar "https://dummyimage.com/300x300/000/fff"
            :title "Time Align"
            :subtitle "Bring what you want closer to what you do"
            :text "A clojure open source web application for planning and tracking time. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long. What happens when this is like ubbber long."
            :github "https://github.com/goodsoftwareengineering/time-align"
            :link "https://timealign.io"
            :media-image "https://dummyimage.com/1600x900/000/fff"}])

(defn app-card [{:keys [avatar title subtitle text github link media-image]}]
   [ui/card {:key (str title subtitle "app-card")}

    [ui/card-header (merge
                     (when (some? avatar) {:avatar avatar})
                     (when (some? title) {:title title})
                     (when (some? subtitle) {:subtitle subtitle})
                     (when (some? media-image) {:actAsExpander true
                                                :showExpandableButton true}))]
    (when (some? media-image)
      [ui/card-media {:expandable true}
       [:img {:src media-image}]])

    (when (some? text) [ui/card-text {:expandable true} [:p text]])

    [ui/card-text
     [:div (stylefy/use-style style-card-text)
      (when (some? github) [ui/flat-button {:href github :label "github"
                                            :primary true}])
      (when (some? link)   [ui/flat-button {:href link :label title
                                            :primary true}])]]])

(defn apps-page []
  [:div (doall (->> apps (map app-card)))])
