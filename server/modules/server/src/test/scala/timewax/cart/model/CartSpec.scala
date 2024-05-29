package timewax.cart.model

import zio._
import zio.test._

// TODO this is boilerplate at this point, remove?
// noinspection TypeAnnotation to allow for cleaner specs
object CartSpec extends ZIOSpecDefault {

  override def spec =
    suite("Cart")(
      test("create empty cart") {
        val program = for {
          cart <- ZIO.succeed(Cart.empty)
        } yield cart

        assertZIO(program)(Assertion.assertion("cart should be empty") { cart =>
          cart.content == Map.empty
        })
      }
    ).provide(
      // nothing needed here
    )
}
