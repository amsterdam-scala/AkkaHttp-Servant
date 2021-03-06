lazy val servant = project
  .copy(id = "servant")
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning, JavaAppPackaging, DockerPlugin)

name := "servant"

libraryDependencies ++= Seq(Library.akkaHttp)

initialCommands := """|import de.heikoseeberger.servant._
                      |""".stripMargin

maintainer.in(Docker) := "Heiko Seeberger"
daemonUser.in(Docker) := "root"
dockerBaseImage       := "java:8"
dockerRepository      := Some("hseeberger")
dockerExposedPorts    := Seq(8000)
