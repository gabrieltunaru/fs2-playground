val scala3Version = "3.2.1"

val fs2V = "3.4.0"
val catsV = "2.9.0"
val catsEffectV = "3.4.4"
val kafkaV = "2.5.0"
val log4SV = "1.10.0"

lazy val root = project
  .in(file("."))
  .settings(
    organization := "com.cannondev",
    name := "fs2",
    semanticdbEnabled := true,
    version := "0.1.1",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "co.fs2" %% "fs2-core" % fs2V,
      "co.fs2" %% "fs2-io" % fs2V,
      "co.fs2" %% "fs2-reactive-streams" % fs2V,
      "org.typelevel" %% "cats-core" % catsV,
      "org.typelevel" %% "cats-effect" % catsEffectV,
      "com.github.fd4s" %% "fs2-kafka" % kafkaV,
      "org.log4s" %% "log4s" % log4SV
    ),
    scalacOptions += "-new-syntax"
  )

mainClass in (Compile, run) := Some("com.cannondev.fs2.RandomProducer")
