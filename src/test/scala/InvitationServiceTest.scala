
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.Unmarshal
import model.Invitation
import org.scalatest.{Matchers, WordSpec}
import routes.InvitationRoute

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class InvitationServiceTest extends WordSpec with Matchers with ScalatestRouteTest with InvitationRoute{
  "Rest Interface" should {
    "Respond healthy " in {

      val getHealthy = HttpRequest(HttpMethods.GET, uri = "/healthcheck")



      getHealthy ~> invitationServiceRoute ~> check {
        status.isSuccess() shouldEqual true
        val entityData = Unmarshal(response.entity).to[String]
        entityData.isCompleted shouldEqual true
        val returnedData = Await.result(entityData, 2 seconds)
        returnedData shouldEqual "Alive & Healthy"
      }
    }
    "Respond with empty List" in {
      val getInvitations = HttpRequest(HttpMethods.GET, uri = "/invitation")

      getInvitations ~> invitationServiceRoute ~> check {
        status.isSuccess() shouldEqual true
        val entityData = Unmarshal(response.entity).to[List[Invitation]]
        entityData.isCompleted shouldEqual true
        val returnedData = Await.result(entityData, 2 seconds)
        returnedData should have size(0)
      }
    }
  }
}
