name := "PerfList"

version := "0.1"

scalaVersion := "2.13.8"

scalacOptions ++= Seq("-language:implicitConversions", "-deprecation")

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.12"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.12" % "test"
libraryDependencies += "com.storm-enroute" %% "scalameter-core" % "0.21"
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
libraryDependencies += "org.scalameta" %% "munit" % "0.7.29"

// scalameter depends on 3.4, avoid conflict during assembly with the grader
dependencyOverrides ++= Seq(
    "org.apache.commons" % "commons-lang3" % "3.12.0"
)

val MUnitFramework = new TestFramework("munit.Framework")
testFrameworks += MUnitFramework
// Decode Scala names
testOptions += Tests.Argument(MUnitFramework, "-s")
