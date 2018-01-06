(ns app.nav
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
            [app.global-styles :as global-styles]))


(def style-navbar {:display "flex"
                   :flex-direction "row"
                   :flex-wrap "nowrap"})

(def style-nav-svg-icon {:color (:alternateTextColor global-styles/palette)})

(def nav-links [{:title "Apps"   :uri "#/apps"   :page :apps
                 :icon [ic/navigation-apps style-nav-svg-icon]}
                {:title "Blog"   :uri "#/blog"   :page :blog
                 :icon [ic/action-description style-nav-svg-icon]}
                {:title "Career" :uri "#/career" :page :career
                 :icon [ic/action-work style-nav-svg-icon]}])

(defn nav-link-app-bar [{:keys [uri title page current-page icon]}]
  [ui/flat-button {:key (str title uri page)
                   :label title
                   :href uri
                   :icon (r/as-element [ui/svg-icon icon])
                   :style (merge {:color (:alternateTextColor global-styles/palette)
                                  :marginLeft "1em"}
                                 (when (= page current-page)
                                   {:background-color (:primary2Color global-styles/palette)}))}])

(defn nav-link-drawer [{:keys [uri title page current-page icon]}]
 [ui/menu-item ])

(defn navbar []
  (let [current-page @(rf/subscribe [:page])
        open @(rf/subscribe [:nav-drawer])]
   [ui/paper {:style {:background-color (:primary1Color global-styles/palette)
                      :width "100%"
                      :padding "1em"}}
    [:div (stylefy/use-style style-navbar)
     [:div (stylefy/use-style global-styles/style-responsive-show-on-phone)
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
                              {:color (:alternateTextColor global-styles/palette)}
                              (when (= :home current-page)
                                {:background-color (:primary2Color global-styles/palette)}))}]

     [:div (stylefy/use-style global-styles/style-responsive-hide-on-phone)
      (->> nav-links
           (map #(nav-link-app-bar (merge {:current-page current-page} %))))]]]))

