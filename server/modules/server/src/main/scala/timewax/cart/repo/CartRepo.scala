package timewax.cart.repo

import timewax.cart.model.{Cart, Coupon}
import zio._

trait CartRepo {
  def upsertItem(sku: String, count: Int): Task[String]
  def removeItem(id: String): Task[Unit]
  def getCart: Task[Cart]
  def emptyCart(): Task[Cart]

  def addCoupon(coupon: Coupon): Task[Unit]
  def removeCoupon(id: String): Task[Unit]
}

class CartRepoInMemory extends CartRepo {

  private var cart = Cart.empty

  def upsertItem(sku: String, newCount: Int): Task[String] = {
    cart = cart.copy(content = cart.content.updated(sku, newCount))
    ZIO.succeed(sku)
  }

  override def removeItem(sku: String): Task[Unit] = ZIO.succeed {
    cart = cart.copy(content = cart.content.removed(sku))
  }

  def getCart: Task[Cart] = ZIO.succeed(cart)

  def emptyCart(): Task[Cart] = ZIO.succeed {
    cart = Cart.empty
    cart
  }

  def addCoupon(coupon: Coupon): Task[Unit] =
    ZIO.succeed {
      cart = cart.copy(coupon = Some(coupon.code))
    }
  def removeCoupon(id: String): Task[Unit] =
    ZIO.succeed {
      cart = cart.copy(coupon = None)
    }

}

object CartRepoInMemory {
  val layer: ULayer[CartRepoInMemory] = ZLayer.succeed(new CartRepoInMemory())
}
