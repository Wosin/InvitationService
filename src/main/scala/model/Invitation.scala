package model

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class Invitation(invitee: String, email: String)

object Invitation extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val invitationFormat = jsonFormat2(Invitation.apply)
}