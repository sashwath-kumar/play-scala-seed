package domain.repositories

import java.util.UUID

import scala.concurrent.Future

import domain.models.Connection
import domain.models.ConnectionStatus.ACTIVE
import domain.models.System
import domain.repositories.ConnectionRepository

class ConnectionRepositoryImpl extends ConnectionRepository {

  private val connectionId: UUID = UUID.fromString("8489b443-4671-4e15-b2de-365319e35a5e")
  private val system1Id: UUID    = UUID.fromString("8489b444-4671-4e15-b2de-365319e35a5d")
  private val system2Id: UUID    = UUID.fromString("9489b443-4671-4e15-b2de-365319e35b5e")

  private val connections: Map[UUID, Connection] = Map(
    UUID.fromString("8489b443-4671-4e15-b2de-365319e35a5e") ->
      Connection(
        connectionId,
        System(system1Id, "https://ql-dev-1.atlassian.net"),
        System(system2Id, "https://ql-dev-2.atlassian.net"),
        ACTIVE
      )
  )

  override def get(connectionId: UUID): Future[Option[Connection]] = {
    Future.successful(connections.get(connectionId))
  }

}
