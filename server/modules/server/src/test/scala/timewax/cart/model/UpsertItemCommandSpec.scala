package timewax.cart.model

import zio.test._
import zio.test.Assertion._

// noinspection TypeAnnotation to allow for cleaner specs
object UpsertItemCommandSpec extends ZIOSpecDefault {

  override def spec =
    suite("UpsertItemCommandSpec")(
      test("create order of less than 1 fails") {
        val errorOrCmd = UpsertItemCommand.from("acme", 0)
        assert(errorOrCmd)(isLeft)
      }
    )
}
