package timewax.cart.service

import timewax.cart.http.UpsertItemRequest
import timewax.cart.model.BusinessException
import timewax.cart.repo.CartRepoInMemory
import timewax.cart.services.{CartService, CartServiceDemo}
import zio._
import zio.test._
import zio.test.Assertion._

// noinspection TypeAnnotation to allow for cleaner specs
object CartServiceSpec extends ZIOSpecDefault {

  override def spec =
    suite("Cart Service")(
      test("add item") {
        val program = for {
          service <- ZIO.service[CartService]
          cmd     <- ZIO.fromEither(UpsertItemRequest("acme", 1).toCommand)
          _       <- service.addItem(cmd)
          cart    <- service.getCart
        } yield cart

        assertZIO(program)(Assertion.assertion("item should be created") { cart =>
          cart.lineItems.length == 1
        })
      },
      test("add item fails if more than 1000") {
        val program = for {
          service <- ZIO.service[CartService]
          cmd     <- ZIO.fromEither(UpsertItemRequest("acme", 1001).toCommand)
          _       <- service.addItem(cmd)
          cart    <- service.getCart
        } yield cart

        // FIXME test message
        assertZIO(program.exit)(fails(isSubtype[BusinessException](anything)))
      },
      test("add valid coupon") {
        val program = for {
          service <- ZIO.service[CartService]
          _       <- service.addCoupon("code-10")
          cart    <- service.getCart
        } yield cart

        assertZIO(program)(Assertion.assertion("coupon should be created") { cart =>
          cart.coupon.get.code == "code-10"
        })
      },
      test("add invalid coupon") {
        val program = for {
          service <- ZIO.service[CartService]
          _       <- service.addCoupon("invalid-coupon-code")
          cart    <- service.getCart
        } yield cart

        // FIXME test message
        assertZIO(program.exit)(fails(isSubtype[BusinessException](anything)))
      },
      test("get cart") {
        val program = for {
          service <- ZIO.service[CartService]
          cmd     <- ZIO.fromEither(UpsertItemRequest("acme", 1).toCommand)
          _       <- service.addItem(cmd)
          _       <- service.addCoupon("code-10")
          cart    <- service.getCart
        } yield cart

        assertZIO(program)(Assertion.assertion("item should be created") { cart =>
          cart.lineItems.head.sku == "acme" && cart.coupon.get.code == "code-10"
        })
      }
    ).provide(
      // repos
      CartRepoInMemory.layer,
      // services
      CartServiceDemo.layer
    )
}
