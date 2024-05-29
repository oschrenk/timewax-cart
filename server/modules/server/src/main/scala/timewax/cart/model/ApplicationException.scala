package timewax.cart.model

abstract class ApplicationException(message: String) extends RuntimeException(message)

case class BusinessException(message: String) extends ApplicationException(message)
