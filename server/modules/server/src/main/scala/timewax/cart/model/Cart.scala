package timewax.cart.model

final case class Cart private (
    content: Map[String, Int],
    coupon: Option[String]
)

object Cart {
  import zio.json._

  implicit val encoderCart: JsonEncoder[Cart] = DeriveJsonEncoder.gen[Cart]
  implicit val decoderCart: JsonDecoder[Cart] = DeriveJsonDecoder.gen[Cart]

  val empty: Cart = Cart(Map.empty, None)
}
