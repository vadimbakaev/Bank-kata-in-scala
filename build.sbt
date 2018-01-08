name := "Bank-kata-in-scala"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest"   % "3.0.4"  % Test,
  "org.mockito"   % "mockito-core" % "2.13.0" % Test
)

scalafmtOnCompile := true
