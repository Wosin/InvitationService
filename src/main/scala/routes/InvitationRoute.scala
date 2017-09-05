package routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server
import akka.http.scaladsl.server.Directives._
import model.Invitation
import spray.json.pimpAny

import scala.collection.mutable.ListBuffer

trait InvitationRoute {
  var invitations = List[Invitation]()
  val invitationServiceRoute: server.Route = path("healthcheck") {
    get {
      complete(StatusCodes.OK, "Alive & Healthy")
    }
  }~
  path("invitation") {
    get {
      complete(StatusCodes.OK, invitations)
    }
  }
}
