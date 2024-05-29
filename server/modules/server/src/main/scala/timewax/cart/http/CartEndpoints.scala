package timewax.cart.http

import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._
import timewax.cart.http.UpsertItemRequest._
import timewax.cart.model.{Cart, MaterializedCart}

trait CartEndpoints extends BaseEndpoint {

  // ***************
  // CART
  // ***************

  // POST /cart { UpsertItemRequest } -> { Item }
  val addItemEndpoint: Endpoint[Unit, UpsertItemRequest, Throwable, Unit, Any] =
    baseEndpoint
      .tag("Cart")
      .name("add item")
      .description("Add item to cart")
      .in("cart")
      .post
      .in(jsonBody[UpsertItemRequest])
      .out(emptyOutput)

  // PATCH /cart { UpdateItemCommand } -> { Item }
  val updateItemEndpoint: Endpoint[Unit, UpsertItemRequest, Throwable, Unit, Any] =
    baseEndpoint
      .tag("Cart")
      .name("update item")
      .description("update item in cart")
      .in("cart")
      .patch
      .in(jsonBody[UpsertItemRequest])
      .out(emptyOutput)

  // DELETE /cart/:id {} -> {}
  val removeItemEndpoint: Endpoint[Unit, String, Throwable, Unit, Any] =
    baseEndpoint
      .tag("Cart")
      .name("remove item")
      .description("Remove item to cart")
      .in("cart")
      .delete
      .in(path[String])
      .out(emptyOutput)

  // GET /cart -> { MaterializedCart }
  val getCartEndpoint: Endpoint[Unit, Unit, Throwable, MaterializedCart, Any] =
    baseEndpoint
      .tag("Cart")
      .name("get cart")
      .description("get cart")
      .in("cart")
      .get
      .out(jsonBody[MaterializedCart])

  // DELETE /cart -> { Cart(Empty) }
  val emptyCartEndpoint: Endpoint[Unit, Unit, Throwable, Cart, Any] =
    baseEndpoint
      .tag("Cart")
      .name("empty cart")
      .description("empty cart")
      .in("cart")
      .delete
      .out(jsonBody[Cart])

  // ***************
  // COUPON
  // ***************

  // PATCH /cart/coupon/:id {} -> {}
  val addCouponEndpoint: Endpoint[Unit, String, Throwable, Unit, Any] =
    baseEndpoint
      .tag("Coupon")
      .name("add coupon")
      .description("Add coupon to cart")
      .in("cart" / "coupon")
      .patch
      .in(path[String])
      .out(emptyOutput)

  // DELETE /cart/coupon/:id {} -> {}
  val removeCouponEndpoint: Endpoint[Unit, String, Throwable, Unit, Any] =
    baseEndpoint
      .tag("Cart")
      .name("remove coupon")
      .description("Remove coupon ")
      .in("cart" / "coupon")
      .delete
      .in(path[String])
      .out(emptyOutput)

}
