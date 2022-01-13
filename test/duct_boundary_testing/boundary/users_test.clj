(ns duct-boundary-testing.boundary.users-test
  (:require [clojure.test :refer :all]
            [duct-boundary-testing.boundary.users :refer :all]

            ;; add
            [duct.core :as duct]
            [integrant.core :as ig]
            dev
            ))

;;============================================================

(def profiles [:duct.profile/test])

(def config (duct/prep-config (dev/read-config) profiles))

(defn init-system [cnfg]
  (ig/init cnfg [:duct.migrator/ragtime]))

;;============================================================

(deftest ^:boundary route-test
  (testing "boundary -found-"

    ;; db {:connection-uri "jdbc:mysql://~"}
    (let [db (:duct.database/sql config)]

      ;; migration
      (init-system config)

      (is (= '({:id 1, :name "test_user", :age 20, :email "test_user@yahoo.co.jp"})
             (get-users db)))
        )))
