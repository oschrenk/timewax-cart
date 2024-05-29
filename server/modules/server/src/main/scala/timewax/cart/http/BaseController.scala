package timewax.cart.http

import sttp.tapir.server.ServerEndpoint
import zio._

trait BaseController {
  val routes: List[ServerEndpoint[Any, Task]]
}
