name := """alerts"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
lazy val doobieVersion = "0.4.0"
lazy val playVersion = "42.0.0"

scalaVersion := "2.11.7"
routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  evolutions,
  "org.postgresql" % "postgresql" % playVersion,
  "org.tpolecat" %% "doobie-core-cats" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres-cats" % doobieVersion,
  "org.typelevel" %% "cats" % "0.9.0",
  "com.typesafe.play"  %% "play-json"  %   "2.4.3",
  "com.typesafe.akka" %% "akka-actor" % "2.4.14",
  "org.scalatestplus.play"   %% "scalatestplus-play"  %   "1.5.1" % Test,
  "org.scalaz"    %%   "scalaz-core"  %   "7.1.7",
  "org.scalaz" %% "scalaz-concurrent" % "7.1.3",
  "org.mockito" % "mockito-core" % "1.9.5",
  "joda-time" % "joda-time" % "2.9.7",
  "org.joda" % "joda-convert" % "1.2"
//  "org.tpolecat" %% "doobie-contrib-postgresql" % "0.3.0"
)
