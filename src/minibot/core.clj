(ns minibot.core
  (:gen-class)
  (:use irclj.core
        [irclj.connection :exclude [end]]
        [clj-time.core :exclude [extend]])
  (:require clj-time.coerce))

(declare bot bot-params bot-channels)

(defn raw-log [_ _1 s]
  (spit "log" (str s \newline) :append true))

(def bot-params
     {:name "irclj_bug_demonstration"
      :server "irc.freenode.net"
      :fnmap {:raw-log raw-log}})

(def bot-channels ["#flatland"])

(defn chat [s]
  (message bot (first bot-channels) s))

(defn connect-bot []
  (def bot
       (connect (:server bot-params)
                6667
                (:name bot-params)
                :timeout 60000
                :callbacks (:fnmap bot-params)))
  (join bot bot-channels))

(defn ping
  ([] (ping (clj-time.coerce/to-long (now))))
  ([datum] (write-irc-line bot (str "PING " datum))))

(defn disconnect-bot []
  (kill bot))

(defn -main
  "I'm broken"
  [& args]
  (connect-bot))
