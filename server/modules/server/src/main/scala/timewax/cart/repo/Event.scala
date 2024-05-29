package timewax.cart.repo

// marker trait
sealed trait Event
case class UpdatedItemEvent(sku: String, count: Int) extends Event
case class RemovedItemEvent(sku: String)             extends Event
case object EmptiedCartEvent                         extends Event
case class AddedCouponEvent(id: String)              extends Event
case class RemovedCouponEvent(id: String)            extends Event
