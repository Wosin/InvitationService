package routes

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server
import akka.http.scaladsl.server.Directives._
import model.Invitation

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
    }~
    post {
      entity(as[Invitation]) { invitation =>
        if(invitation.email.isEmpty || invitation.invitee.isEmpty) {
          complete(StatusCodes.BadRequest, "Invitee or Email is empty!")
        } else if(invitations.contains(invitation)) {
          complete(StatusCodes.OK, "Invitation was already added!")
        } else if(!invitation.email.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\\b")) {
          complete(StatusCodes.BadRequest, "Email has improper format!")
        } else {
          invitations = invitations :+ invitation
          complete(StatusCodes.OK, "Invitation added Succesfully")
        }
      }
    }
  }
}
