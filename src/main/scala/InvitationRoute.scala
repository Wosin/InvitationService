import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

trait InvitationRoute {

  val invitationServiceRoute: server.Route = path("healthcheck") {
    get {
      complete(StatusCodes.OK, "Alive & Healthy")
    }
  }
}
