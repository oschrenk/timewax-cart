package timewax.cart.services

import timewax.cart.model.{Cart, Coupon, MaterializedCart, UpsertItemCommand}
import timewax.cart.repo._
import zio._

trait CartService {
  def getCart: Task[MaterializedCart]
  def addItem(req: UpsertItemCommand): Task[Unit]
  def updateItem(req: UpsertItemCommand): Task[Unit]
  def removeItem(id: String): Task[Unit]
  def emptyCart: Task[Cart]

  def addCoupon(code: String): Task[Unit]
  def removeCoupon(code: String): Task[Unit]
}

class CartServiceDemo private (
    cartRepo: CartRepo,
    eventStore: EventStore
) extends CartService {

  override def addItem(cmd: UpsertItemCommand): Task[Unit] = updateItem(cmd)

  override def updateItem(cmd: UpsertItemCommand): Task[Unit] =
    for {
      _ <- cartRepo.upsertItem(cmd.sku, cmd.count)
      _ <- eventStore.publish(UpdatedItemEvent(cmd.sku, cmd.count))
    } yield ()

  override def removeItem(sku: String): Task[Unit] = {
    for {
      item <- cartRepo.removeItem(sku)
      _    <- eventStore.publish(RemovedItemEvent(sku))
    } yield item
  }
  override def emptyCart: Task[Cart] =
    for {
      cart <- cartRepo.emptyCart()
      _    <- eventStore.publish(EmptiedCartEvent)
    } yield cart

  override def addCoupon(code: String): Task[Unit] = {
    for {
      coupon <- ZIO.fromEither(Coupon.fromCode(code))
      cart   <- cartRepo.addCoupon(coupon)
      _      <- eventStore.publish(AddedCouponEvent(code))
    } yield cart

  }
  override def removeCoupon(code: String): Task[Unit] = {
    for {
      cart <- cartRepo.removeCoupon(code)
      _    <- eventStore.publish(RemovedCouponEvent(code))
    } yield cart

  }

  override def getCart: Task[MaterializedCart] = for {
    cart <- cartRepo.getCart
  } yield MaterializedCart.fromCart(cart)

}

object CartServiceDemo {
  val layer: ZLayer[CartRepo, Nothing, CartService] =
    ZLayer {
      ZIO
        .service[CartRepo]
        .map(repo => new CartServiceDemo(repo, new EventStore))
    }
}
