package controllers.swagger

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import org.apache.pekko.stream.scaladsl.StreamConverters
import org.apache.pekko.stream.Materializer
import play.api.http.ContentTypes
import play.api.mvc._
import play.api.Environment
import play.api.Logger

object CustomContentTypes {
  val swaggerYaml = "application/x-yaml"
}

class SwaggerController @Inject() (
                                    val controllerComponents: ControllerComponents,
                                    env: Environment
                                  )(implicit ec: ExecutionContext, mat: Materializer) extends BaseController {

  private val logger = Logger(this.getClass)

  def swaggerDoc: Action[AnyContent] = Action.async {
    env.resourceAsStream("/public/docs/swagger.yaml") match {
      case Some(file) =>
        logger.info("Swagger documentation file found, serving the file.")
        val source = StreamConverters.fromInputStream(() => file)
        Future.successful(Ok.chunked(source).as(CustomContentTypes.swaggerYaml))

      case None =>
        logger.error("Swagger documentation file not found.")
        Future.successful(NotFound("Swagger Documentation file not found."))
    }
  }

  def swaggerUI: Action[AnyContent] = Action {
    val file = env.getFile("public/swagger-ui/Swagger-UI.html")
    Ok.sendFile(file)
  }
}