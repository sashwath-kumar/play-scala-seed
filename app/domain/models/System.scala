package domain.models

import java.util.UUID

import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class System(id: UUID, url: String)

object System {
  implicit val JsonFormat: OFormat[System] = Json.format[System]
}
