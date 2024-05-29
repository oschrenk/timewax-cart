package timewax.cart.http

import sttp.tapir._

trait BaseEndpoint {
  val baseEndpoint: Endpoint[Unit, Unit, Throwable, Unit, Any] = {
    endpoint
      .errorOut(statusCode and plainBody[String])
      .mapErrorOut[Throwable](HttpError.decode _)(HttpError.encode)
  }
}
