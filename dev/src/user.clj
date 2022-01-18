(ns user)

(defn dev
  "Load and switch to the 'dev' namespace."
  []
  (require 'dev)
  (in-ns 'dev)
  :loaded)

;; add
(def migration-db-urls
  {"dev"  "jdbc:mysql://localhost:3306/dev?user=root&password=password"
   "test" "jdbc:mysql://localhost:3306/test?user=root&password=password"})

(defn- migration-config [env]
  {:datastore  (jdbc/sql-database {:connection-uri (migration-db-urls env)})
   :migrations (jdbc/load-resources "migrations")})

(defn migrate [env]
  (repl/migrate (migration-config env)))

(defn rollback [env]
  (repl/rollback (migration-config env)))
