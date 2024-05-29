package timewax.cart.model

final case class ItemInfo(description: String, price: Int)

object ItemInfo {
  import zio.json._

  implicit val encoderLine: JsonEncoder[ItemInfo] = DeriveJsonEncoder.gen[ItemInfo]
  implicit val decoderLine: JsonDecoder[ItemInfo] = DeriveJsonDecoder.gen[ItemInfo]

}
