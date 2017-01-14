package shareevent

import java.util.concurrent.Future

trait Repository[T, K] {

  def store(entity: T): Future[Unit]

  def retrieve(id: K): Future[Option[T]]
}

