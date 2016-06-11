/*
 * Copyright 2016 Heiko Seeberger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.heikoseeberger.servant

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model.StatusCodes.PermanentRedirect
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{ Failure, Success }

object ServantApp {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val mat = ActorMaterializer()
    import system.dispatcher

    val address = system.settings.config.getString("servant.address")
    val port = system.settings.config.getInt("servant.port")
    val directory = system.settings.config.getString("servant.directory")
    val log = Logging(system, getClass)

    Http().bindAndHandle(route(directory), address, port).onComplete {
      case Success(ServerBinding(address)) =>
        log.info("Listening on {}", address)
      case Failure(cause) =>
        log.error(cause, s"Terminating, because can't bind to $address:$port!")
        system.terminate()
    }

    Await.ready(system.whenTerminated, Duration.Inf)
  }

  private def route(directory: String) = {
    import Directives._
    getFromDirectory(directory) ~ pathSingleSlash(get(redirect("index.html", PermanentRedirect)))
  }
}
