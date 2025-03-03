package domain.repositories

import java.util.UUID

import scala.concurrent.Future

import com.google.inject.ImplementedBy
import domain.models.Connection
import domain.services.ConnectionService

@ImplementedBy(classOf[ConnectionRepositoryImpl])
trait ConnectionRepository {

  def get(connectionId: UUID): Future[Option[Connection]]

}
