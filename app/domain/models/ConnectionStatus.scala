package domain.models

import play.api.libs.json._

sealed trait ConnectionStatus

object ConnectionStatus {
  case object ACTIVE extends ConnectionStatus
  case object DEACTIVATED extends ConnectionStatus

  val values: Set[ConnectionStatus] = Set(ACTIVE, DEACTIVATED)

  implicit val writes: Writes[ConnectionStatus] = Writes { status =>
    JsString(status.toString)
  }

  implicit val reads: Reads[ConnectionStatus] = Reads {
    case JsString(value) =>
      values.find(_.toString == value) match {
        case Some(status) => JsSuccess(status)
        case None         => JsError(s"Unknown connection status: $value")
      }
    case other => JsError(s"Expected JsString, but got: $other")
  }

  implicit val format: Format[ConnectionStatus] = Format(reads, writes)
}