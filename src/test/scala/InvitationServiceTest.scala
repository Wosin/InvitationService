
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

class InvitationServiceTest extends WordSpec with Matchers with ScalatestRouteTest with InvitationRoute{
  "Rest Interface" should {
    "Respond healthy " in {

      val getHealthy = HttpRequest(HttpMethods.GET, uri = "/healthcheck")



      getHealthy ~> invitationServiceRoute ~> check {
        status.isSuccess() shouldEqual true
        response.entity.toString.contains("Alive & Healthy") shouldEqual true
      }
    }

  }
}
