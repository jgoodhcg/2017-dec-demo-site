(ns app.pages.blog
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.events]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [stylefy.core :as stylefy]
            [app.global-styles :as global-styles]
            [markdown.core :refer [md->html]]))


(defn blog-page []
  ;; [:div.container
  ;;  (when-let [docs @(rf/subscribe [:docs])]
  ;;    [:div.row>div.col-sm-12
  ;;     [:div {:dangerouslySetInnerHTML
  ;;            {:__html (md->html docs)}}]])]
  [:div "blog page"])

(defn blog-list-page []
  [:div "blog-list-page"])



