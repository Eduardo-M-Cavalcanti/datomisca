name := "datomisca-getting-started"

organization := "pellucidanalytics"

version := "0.2"

scalaVersion := "2.10.0"

resolvers ++= Seq(
  "datomisca-repo snapshots" at "https://github.com/pellucidanalytics/datomisca-repo/raw/master/snapshots",
  "datomisca-repo releases"  at "https://github.com/pellucidanalytics/datomisca-repo/raw/master/releases",
  "clojars" at "https://clojars.org/repo"
)

libraryDependencies ++= Seq(
  "pellucidanalytics" %% "datomisca" % "0.2",
  "com.datomic" % "datomic-free" % "0.8.3814"
)
