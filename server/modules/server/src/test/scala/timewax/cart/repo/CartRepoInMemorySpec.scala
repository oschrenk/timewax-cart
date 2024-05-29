package timewax.cart.repo

import zio._
import zio.test._

// TODO something like this should be an Integration test
// noinspection TypeAnnotation to allow for cleaner specs
object CartRepoInMemorySpec extends ZIOSpecDefault {

  private val dummySku = "acme"

  override def spec =
    suite("Cart Repository")(
      test("upserting item takes latest") {
        val program = for {
          repo <- ZIO.service[CartRepo]
          _    <- repo.upsertItem(dummySku, 1)
          _    <- repo.upsertItem(dummySku, 2)
          cart <- repo.getCart
        } yield cart

        assertZIO(program)(Assertion.assertion("item should be created") { cart =>
          cart.content.get(dummySku).contains(2)
        })
      },
      test("empty cart") {
        val program = for {
          repo <- ZIO.service[CartRepo]
          _    <- repo.upsertItem(dummySku, 1)
          _    <- repo.emptyCart()
          cart <- repo.getCart
        } yield cart

        assertZIO(program)(Assertion.assertion("cart should be empty") { cart =>
          !cart.content.contains(dummySku)
        })
      }
    ).provide(
      CartRepoInMemory.layer
    )
}
