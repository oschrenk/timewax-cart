package timewax.cart.model

import timewax.cart.repo.ItemInfoInMemory

final case class LineItem(sku: String, description: String, price: Int, count: Int)
final case class MaterializedCart(
    lineItems: List[LineItem],
    coupon: Option[Coupon],
    // FIXME discuss Int vs Double vs BigDecimal for money
    total: Int
)

object MaterializedCart {
  import zio.json._

  // FIXME this should be a proper repo in service
  // FIXME this should be a layer in service
  // FIXME this should use Random for better testing
  private val itemInfo = new ItemInfoInMemory()

  // FIXME Business decision of rounding done here since we have Int
  // move calculation to Coupon? probably better to have Calculation object
  private def getTotal(lines: List[LineItem], maybeCoupon: Option[Coupon]): Int = {
    val totalWithoutCoupon          = lines.map(l => l.count * l.price).sum
    val effectivePercentage: Double = 100 / maybeCoupon.map(c => 100 - c.percentage).getOrElse(100)
    val total: Int                  = (totalWithoutCoupon * effectivePercentage).toInt
    total
  }

  def fromCart(cart: Cart): MaterializedCart = {
    val items = cart.content.toList
    val lineItem = items.map { item =>
      val sku   = item._1
      val count = item._2
      val info  = itemInfo.getItem(sku)
      LineItem(sku, info.description, info.price, count)
    }

    val maybeCoupon = cart.coupon.flatMap(c => Coupon.fromCode(c).toOption)
    val total       = getTotal(lineItem, maybeCoupon)
    MaterializedCart(lineItem, maybeCoupon, total)
  }

  implicit val encoderLineItem: JsonEncoder[LineItem] =
    DeriveJsonEncoder.gen[LineItem]
  implicit val decoderLineItem: JsonDecoder[LineItem] =
    DeriveJsonDecoder.gen[LineItem]

  implicit val encoderCart: JsonEncoder[MaterializedCart] = DeriveJsonEncoder.gen[MaterializedCart]
  implicit val decoderCart: JsonDecoder[MaterializedCart] = DeriveJsonDecoder.gen[MaterializedCart]

  val empty: Cart = Cart(Map.empty, None)
}
