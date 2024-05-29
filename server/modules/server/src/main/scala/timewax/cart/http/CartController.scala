package timewax.cart.http

import sttp.tapir.server.ServerEndpoint
import timewax.cart.services.CartService
import zio.{Task, ZIO}

class CartController private (cartService: CartService) extends BaseController with CartEndpoints {

  // ***************
  // ITEMs
  // ***************

  private val getCart: ServerEndpoint[Any, Task] = getCartEndpoint
    .serverLogic(_ => cartService.getCart.either)

  private val addItem: ServerEndpoint[Any, Task] = addItemEndpoint
    .serverLogic { req =>
      val program = for {
        command <- ZIO.fromEither(req.toCommand)
        _       <- cartService.addItem(command)
      } yield ()
      program.either
    }

  private val updateItem: ServerEndpoint[Any, Task] = updateItemEndpoint
    .serverLogic { req =>
      val program = for {
        command <- ZIO.fromEither(req.toCommand)
        _       <- cartService.updateItem(command)
      } yield ()
      program.either
    }

  private val removeItem: ServerEndpoint[Any, Task] = removeItemEndpoint
    .serverLogic { id => cartService.removeItem(id).either }

  private val emptyCart: ServerEndpoint[Any, Task] = emptyCartEndpoint
    .serverLogic(_ => cartService.emptyCart.either)

  // ***************
  // COUPON
  // ***************
  private val addCoupon: ServerEndpoint[Any, Task] = addCouponEndpoint
    .serverLogic(id => cartService.addCoupon(id).either)

  private val removeCoupon: ServerEndpoint[Any, Task] = removeCouponEndpoint
    .serverLogic(id => cartService.removeCoupon(id).either)

  override val routes: List[ServerEndpoint[Any, Task]] =
    List(getCart, addItem, updateItem, removeItem, emptyCart, addCoupon, removeCoupon)
}

object CartController {
  def makeZIO: ZIO[CartService, Nothing, CartController] = for {
    service <- ZIO.service[CartService]
  } yield new CartController(service)
}
