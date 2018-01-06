(ns app.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [ajax.core :refer [GET POST]]
            [app.ajax :refer [load-interceptors!]]
            [app.events]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [stylefy.core :as stylefy]
            [app.nav :as nav]
            [app.global-styles :as global-styles])
  (:import goog.History))

(defn about-page []
  [:div.container
   [:div.row
    [:div.col-md-12
     [:img {:src (str js/context "/img/warning_clojure.png")}]]]])

(defn home-page []
  [ui/paper [:div [:p "hello material ui paper"]]]
  ;; [:div.container
  ;;  (when-let [docs @(rf/subscribe [:docs])]
  ;;    [:div.row>div.col-sm-12
  ;;     [:div {:dangerouslySetInnerHTML
  ;;            {:__html (md->html docs)}}]])]
  )

(def pages
  {:home #'home-page
   :about #'about-page})

(def style-base-page {:background-color  (color :grey)
                      :height "100%"})

(defn page []
  [ui/mui-theme-provider
   {:mui-theme (get-mui-theme {:palette global-styles/palette})}

   [:div (stylefy/use-style style-base-page)
    (nav/navbar)
    [:div (stylefy/use-style {:padding "1em"})
     [(pages @(rf/subscribe [:page]))]]]])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (rf/dispatch [:set-active-page :home]))

(secretary/defroute "/apps" []
  (rf/dispatch [:set-active-page :apps]))

(secretary/defroute "/career" []
  (rf/dispatch [:set-active-page :career]))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      HistoryEventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn fetch-docs! []
  (GET "/docs" {:handler #(rf/dispatch [:set-docs %])}))

(defn mount-components []
  (rf/clear-subscription-cache!)
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch-sync [:initialize-db])
  (load-interceptors!)
  (fetch-docs!)
  (hook-browser-navigation!)
  (mount-components)
  (stylefy/init))
