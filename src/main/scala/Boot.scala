import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import routes.InvitationRoute

object Boot extends App with InvitationRoute {
  implicit val system: ActorSystem = ActorSystem("InvitationRestService")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  val binding = Http().bindAndHandle(invitationServiceRoute, "localhost", 8080)

}

