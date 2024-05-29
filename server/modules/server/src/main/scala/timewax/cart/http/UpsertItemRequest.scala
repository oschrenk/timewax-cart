package timewax.cart.http

import timewax.cart.model.UpsertItemCommand
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

final case class UpsertItemRequest(
    sku: String,
    count: Int
) {
  def toCommand: Either[Throwable, UpsertItemCommand] = {
    UpsertItemCommand.from(sku, count)
  }
}

object UpsertItemRequest {

  implicit val encoder: JsonEncoder[UpsertItemRequest] =
    DeriveJsonEncoder.gen[UpsertItemRequest]
  implicit val decoder: JsonDecoder[UpsertItemRequest] =
    DeriveJsonDecoder.gen[UpsertItemRequest]
}
