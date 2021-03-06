import com.typesafe.sbt.{ GitPlugin, SbtNativePackager, SbtScalariform }
import de.heikoseeberger.sbtheader.HeaderPlugin
import de.heikoseeberger.sbtheader.license.Apache2_0
import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin
import scalariform.formatter.preferences.{ AlignSingleLineCaseStatements/*, DoubleIndentClassDeclaration*/ }

object Build extends AutoPlugin {

  override def requires = JvmPlugin && HeaderPlugin && GitPlugin && SbtScalariform && SbtNativePackager

  override def trigger = allRequirements

  override def projectSettings = Seq(
    // Core settings
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
    organization := "de.heikoseeberger",
    scalaVersion := Version.Scala,
    crossScalaVersions := Seq(scalaVersion.value),
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-target:jvm-1.8",
      "-encoding", "UTF-8"
    ),
    unmanagedSourceDirectories.in(Compile) := Seq(scalaSource.in(Compile).value),
    unmanagedSourceDirectories.in(Test) := Seq(scalaSource.in(Test).value),

    // Scalariform settings
    SbtScalariform.autoImport.scalariformPreferences := SbtScalariform.autoImport.scalariformPreferences.value
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
      /*.setPreference(DoubleIndentClassDeclaration, true)*/,

    // Git settings
    GitPlugin.autoImport.git.useGitDescribe := true,

    // Header settings
    HeaderPlugin.autoImport.headers := Map("scala" -> Apache2_0("2016", "Heiko Seeberger"))
  )
}
