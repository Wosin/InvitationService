import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import routes.InvitationRoute
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Boot extends InvitationRoute {

   def main(args: Array[String]): Unit = {
    println("Run run.sh [host] [port] to override default host and port")
    val defaultPort = 8080
    val defaultHost = "localhost"
    var userDefinedPort:Option[Int] = Option.empty
    var userDefinedHost:Option[String] = Option.empty
     args foreach(arg =>println(arg))
    args.length match  {
      case 2 => {
        println(s"Using user defined host: ${args(0)}")
        userDefinedHost = Option(args(0))
        println(s"Using user defined port: ${args(1)}")
        userDefinedPort = Option(args(1).toInt)
      }
      case _ => print("Using default host: localhost and port: 8080")

      }


    val applicationPort:Int = if(userDefinedPort.isEmpty) defaultPort else userDefinedPort.get
    val applicationHost:String = if(userDefinedHost.isEmpty) defaultHost else userDefinedHost.get
    implicit val system: ActorSystem = ActorSystem("InvitationRestService")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    val binding = Http().bindAndHandle(invitationServiceRoute, applicationHost, applicationPort)
    binding.onComplete {
      case Success(_) =>
        println(s"Server started succesfully and is listening on $applicationHost:$applicationPort")
      case Failure(reason) =>
        println("Failed to start server. Reason:", reason)
    }

  }

}

