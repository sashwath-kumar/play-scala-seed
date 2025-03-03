package controllers

import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import scala.util.Try

import domain.services.ConnectionService
import play.api.libs.json.JsString
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.BaseController
import play.api.mvc.ControllerComponents

@Singleton
class ConnectionController @Inject() (
    val controllerComponents: ControllerComponents,
    connectionService: ConnectionService
)(implicit val ec: ExecutionContext)
    extends BaseController {

  def getConnection(connectionId: String): Action[AnyContent] = Action.async {
    Try {
      UUID.fromString(connectionId)
    } match {
      case Failure(exception) =>
        Future.successful(
          InternalServerError(Json.obj("message" -> "Connection ID is not parsable to UUID"))
        )
      case Success(connectionUUId) =>
        connectionService
          .findById(connectionUUId)
          .map {
            case Left(error)       => NotFound(Json.obj("message" -> JsString("Error")))
            case Right(connection) => Ok(Json.toJson(connection))
          }
          .recover { case ex =>
            InternalServerError(Json.obj("message" -> s"An unexpected error occurred: ${ex.getMessage}"))
          }
    }
  }
}
