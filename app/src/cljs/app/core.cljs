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
            [stylefy.core :as stylefy])
  (:import goog.History))

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

(defn nav-link-app-bar [{:keys [uri title page current-page icon]}]
    [ui/flat-button {:key (str title uri page)
                     :label title
                     :href uri
                     :icon (r/as-element [ui/svg-icon icon])
                     :style (merge {:color (:alternateTextColor palette)
                                    :marginLeft "1em"}
                                   (when (= page current-page)
                                     {:background-color (:primary2Color palette)}))}])

(def phone-width 571)

(def style-responsive-show-on-phone {:display "none"
                                     ::stylefy/media {{:max-width (str phone-width "px")}
                                                             {:display "flex"}}})

(def style-responsive-hide-on-phone {:display "none"
                                     ::stylefy/media {{:min-width (str (+ 1 phone-width) "px")}
                                                      {:display "flex"}}})

(def style-navbar {:display "flex"
                   :flex-direction "row"
                   :flex-wrap "nowrap"})

(def style-nav-svg-icon {:color (:alternateTextColor palette)})

(def nav-links [{:title "Apps"   :uri "#/apps"   :page :apps
                 :icon [ic/navigation-apps style-nav-svg-icon]}
                {:title "Blog"   :uri "#/blog"   :page :blog
                 :icon [ic/action-description style-nav-svg-icon]}
                {:title "Career" :uri "#/career" :page :career
                 :icon [ic/action-work style-nav-svg-icon]}])

(defn navbar []
  (let [current-page @(rf/subscribe [:page])
        open @(rf/subscribe [:nav-drawer])]
   [ui/paper {:style {:background-color (:primary1Color palette)
                      :width "100%"
                      :padding "1em"}}
    [:div (stylefy/use-style style-navbar)
     [:div (stylefy/use-style style-responsive-show-on-phone)
      [ui/flat-button {:icon (r/as-element [ui/svg-icon
                                            [ic/navigation-menu style-nav-svg-icon]])
                       :on-click #(rf/dispatch [:toggle-nav-drawer])}]]

     [ui/drawer {:open open
                 :docked             false
                 :disableSwipeToOpen true
                 :onRequestChange (fn [_] (rf/dispatch [:toggle-nav-drawer]))}]

     [ui/flat-button {:href "#/" :label "Justin Good"
                      :icon (r/as-element [ui/svg-icon [ic/social-person style-nav-svg-icon]])
                      :style (merge
                              {:color (:alternateTextColor palette)}
                              (when (= :home current-page)
                                {:background-color (:primary2Color palette)}))}]

     [:div (stylefy/use-style style-responsive-hide-on-phone)
      (->> nav-links
           (map #(nav-link-app-bar (merge {:current-page current-page} %))))]]]))

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
   {:mui-theme (get-mui-theme {:palette palette})}

   [:div (stylefy/use-style style-base-page)
    [navbar]
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
