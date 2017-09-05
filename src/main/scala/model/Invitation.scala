package model

import spray.json.DefaultJsonProtocol

case class Invitation(invitee: String, email: String)

object Invitation extends DefaultJsonProtocol {
  implicit val invitationFormat = jsonFormat2(Invitation.apply)
}