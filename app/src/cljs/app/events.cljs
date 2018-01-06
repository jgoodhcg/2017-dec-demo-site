(ns app.events
  (:require [app.db :as db]
            [re-frame.core :refer [dispatch reg-event-db reg-sub]]))

;;dispatchers

(reg-event-db
  :initialize-db
  (fn [_ _]
    db/default-db))

(reg-event-db
  :set-active-page
  (fn [db [_ page]]
    (assoc db :page page)))

(reg-event-db
  :set-docs
  (fn [db [_ docs]]
    (assoc db :docs docs)))

(reg-event-db
 :toggle-nav-drawer
 (fn [db [_ _]]
      ;; TODO specter please
      (assoc-in db [:view :nav-drawer] (not (get-in db [:view :nav-drawer])))))

;;subscriptions

(reg-sub
  :page
  (fn [db _]
    (:page db)))

(reg-sub
  :docs
  (fn [db _]
    (:docs db)))

(reg-sub
 :nav-drawer
 (fn [db _]
   (get-in db [:view :nav-drawer])))
