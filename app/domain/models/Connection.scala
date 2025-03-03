package domain.models

import java.util.UUID

import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class Connection(id: UUID, system1: System, system2: System, status: ConnectionStatus)

object Connection {
  implicit val JsonFormat: OFormat[Connection] = Json.format[Connection]
}
