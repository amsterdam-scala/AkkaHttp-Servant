import sbt._

object Version {
  final val Akka  = "2.4.7"
  final val Scala = "2.11.8"
}

object Library {
  val akkaHttp = "com.typesafe.akka" %% "akka-http-experimental" % Version.Akka
}
