package timewax.cart.model

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class Coupon(code: String, percentage: Int)

object Coupon {

  implicit val encoder: JsonEncoder[Coupon] = DeriveJsonEncoder.gen[Coupon]
  implicit val decoder: JsonDecoder[Coupon] = DeriveJsonDecoder.gen[Coupon]

  private val map = Map(
    "code-10" -> Coupon("code-10", 10),
    "code-20" -> Coupon("code-20", 20)
  )
  def fromCode(code: String): Either[Throwable, Coupon] = {
    map.get(code).toRight(BusinessException("No valid coupon"))
  }
}
