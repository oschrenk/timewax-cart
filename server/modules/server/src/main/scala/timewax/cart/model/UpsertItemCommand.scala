package timewax.cart.model

case class UpsertItemCommand private (sku: String, count: Int)
object UpsertItemCommand {
  private val maxCount = 1000

  def from(sku: String, count: Int): Either[Throwable, UpsertItemCommand] = {
    if (count <= 0)
      Left(BusinessException("Count too low"))
    else if (count > maxCount)
      Left(BusinessException("Count too high"))
    else
      Right(UpsertItemCommand(sku, count))

  }
}
