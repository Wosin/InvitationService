name := "RestInterface"

version := "1.0"

scalaVersion := "2.12.3"

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.19"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.0.10"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.10"
libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "10.0.10" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

