lazy val servant = project
  .copy(id = "servant")
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning)

name := "servant"

libraryDependencies ++= Vector(
  Library.scalaTest % "test"
)

initialCommands := """|import de.heikoseeberger.servant._
                      |""".stripMargin
