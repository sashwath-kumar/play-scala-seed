package domain.services

import java.util.UUID
import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.Future


import domain.models.Connection
import domain.models.ConnectionStatus.ACTIVE
import domain.repositories.ConnectionRepository

class ConnectionService @Inject() (connectionRepository: ConnectionRepository)(implicit val ec: ExecutionContext) {

  def findById(connectionId: UUID): Future[Either["Error", Connection]] = {
    connectionRepository.get(connectionId).map {
      case Some(connection) => Right(connection)
      case None             => Left("Error")
    }
  }
}
