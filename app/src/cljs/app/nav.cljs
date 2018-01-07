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
                 :icon-nav [ic/navigation-apps style-nav-svg-icon]
                 :icon-drawer [ic/navigation-apps]}

                {:title "Blog"   :uri "#/blog"   :page :blog-list
                 :icon-nav [ic/action-description style-nav-svg-icon]
                 :icon-drawer [ic/action-description]}

                {:title "Career" :uri "#/career" :page :career
                 :icon-nav [ic/action-work style-nav-svg-icon]
                 :icon-drawer [ic/action-work]}])

(defn nav-link-app-bar [{:keys [uri title page current-page icon-nav]}]
  [ui/flat-button {:key (str title uri page "nav")
                   :label title
                   :href uri
                   :icon (r/as-element [ui/svg-icon icon-nav])
                   :style (merge {:color (:alternateTextColor global-styles/palette)
                                  :marginLeft "1em"}
                                 (when (= page current-page)
                                   {:background-color (:primary2Color global-styles/palette)}))}])

(defn nav-link-drawer [{:keys [uri title page current-page icon-drawer]}]
  [ui/menu-item {:key (str title uri page "drawer")
                 :leftIcon (r/as-element [ui/svg-icon icon-drawer]) ;; TODO icon should part of the link
                 :onClick #(rf/dispatch [:toggle-nav-drawer])
                 :children (r/as-element [:a {:href uri
                                              :style {:text-decoration "none"
                                                      :color (:text-color global-styles/palette)}}
                                          [:div ]title])}])

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
                 :onRequestChange (fn [_] (rf/dispatch [:toggle-nav-drawer]))}
      (->> nav-links
           (map #(nav-link-drawer (merge {:current-page current-page} %))))]

     (->> {:title "Justin Good" :uri "#/" :page :home :current-page current-page
           :icon-nav [ic/social-person style-nav-svg-icon]}
          (nav-link-app-bar))

     [:div (stylefy/use-style global-styles/style-responsive-hide-on-phone)
      (->> nav-links
           (map #(nav-link-app-bar (merge {:current-page current-page} %))))]]]))

