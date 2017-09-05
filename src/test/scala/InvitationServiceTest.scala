
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.Unmarshal
import model.Invitation
import org.scalatest.{Matchers, WordSpec}
import routes.InvitationRoute
import spray.json.pimpAny

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class InvitationServiceTest extends WordSpec with Matchers with ScalatestRouteTest with InvitationRoute {
  "REST Interface" should {
    "GET Healthcheck should respond with healthy " in {

      val getHealthy = HttpRequest(HttpMethods.GET, uri = "/healthcheck")


      getHealthy ~> invitationServiceRoute ~> check {
        status.isSuccess() shouldEqual true
        val entityData = Unmarshal(response.entity).to[String]
        entityData.isCompleted shouldEqual true
        val returnedData = Await.result(entityData, 2 seconds)
        returnedData shouldEqual "Alive & Healthy"
      }
    }

    "GET Invitations should respond with empty list" in {
      val getInvitations = HttpRequest(HttpMethods.GET, uri = "/invitation")

      getInvitations ~> invitationServiceRoute ~> check {
        status.isSuccess() shouldEqual true
        val entityData = Unmarshal(response.entity).to[List[Invitation]]
        entityData.isCompleted shouldEqual true
        val returnedData = Await.result(entityData, 2 seconds)
        returnedData should have size (0)
      }
    }

    "POST invitation with proper format should respond with success" in {
      val invitationToAdd = Invitation("Domwos", "DomWos@wp.pl").toJson
      val addNewInvitation = HttpRequest(HttpMethods.POST, uri = "/invitation",
        entity = HttpEntity(MediaTypes.`application/json`, invitationToAdd.toString))


      addNewInvitation ~> invitationServiceRoute ~> check {
        status.isSuccess() shouldEqual true
        val entityData = Unmarshal(response.entity).to[String]
        entityData.isCompleted shouldEqual true
        val returnedData = Await.result(entityData, 2 seconds)
        returnedData shouldEqual "Invitation added Succesfully"
      }
    }

    "POST invitation with proper format twice should respond with 'already added'" in {
      val invitationToAdd = Invitation("Domwos", "DomWos@wp.pl").toJson
      val addNewInvitation = HttpRequest(HttpMethods.POST, uri = "/invitation",
        entity = HttpEntity(MediaTypes.`application/json`, invitationToAdd.toString))

      addNewInvitation ~> invitationServiceRoute
      addNewInvitation ~> invitationServiceRoute ~> check {
        status.isSuccess() shouldEqual true
        val entityData = Unmarshal(response.entity).to[String]
        entityData.isCompleted shouldEqual true
        val returnedData = Await.result(entityData, 2 seconds)
        returnedData shouldEqual "Invitation was already added!"
      }
    }

    "POST invitation with badly formatted email should respond with 'email format improper'" in {
      val invitationToAdd = Invitation("DominWos", "ImproperMail").toJson
      val addNewInvitation = HttpRequest(HttpMethods.POST, uri = "/invitation",
        entity = HttpEntity(MediaTypes.`application/json`, invitationToAdd.toString))

      addNewInvitation ~> invitationServiceRoute ~> check {
        status.isFailure() shouldEqual true
        val entityData = Unmarshal(response.entity).to[String]
        entityData.isCompleted shouldEqual true
        val returnedData = Await.result(entityData, 2 seconds)
        returnedData shouldEqual "Email has improper format!"
      }
    }

    "POST invitation with empty fields should respond with 'invitee or name empty'"  in {
      val invitationToAdd = Invitation("", "ImproperMail").toJson
      val addNewInvitation = HttpRequest(HttpMethods.POST, uri = "/invitation",
        entity = HttpEntity(MediaTypes.`application/json`, invitationToAdd.toString))

      addNewInvitation ~> invitationServiceRoute ~> check {
        status.isFailure() shouldEqual true
        val entityData = Unmarshal(response.entity).to[String]
        entityData.isCompleted shouldEqual true
        val returnedData = Await.result(entityData, 2 seconds)
        returnedData shouldEqual "Invitee or Email is empty!"
      }
    }
  }

}
