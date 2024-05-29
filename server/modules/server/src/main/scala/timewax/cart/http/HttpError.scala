package timewax.cart.http

import sttp.model.StatusCode
import timewax.cart.model.BusinessException

final case class HttpError(
    statusCode: StatusCode,
    message: String,
    cause: Throwable
) extends RuntimeException(message, cause)

object HttpError {
  def decode(tuple: (StatusCode, String)): HttpError =
    HttpError(tuple._1, tuple._2, new RuntimeException(tuple._2))

  def encode(error: Throwable): (StatusCode, String) = error match {
    case BusinessException(message) => (StatusCode.UnprocessableEntity, message)
    case _                          => (StatusCode.InternalServerError, error.getMessage)
  }

}
