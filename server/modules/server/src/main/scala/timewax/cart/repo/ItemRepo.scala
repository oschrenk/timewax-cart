package timewax.cart.repo

import timewax.cart.model.ItemInfo

import scala.util.Random

trait ItemInfoRepo {
  def getItem(sku: String): ItemInfo
}

class ItemInfoInMemory extends ItemInfoRepo {
  private val rand     = new scala.util.Random
  private val alphabet = "abcdefghijklmnopqrstuvwxyz"
  private def randStr(n: Int) =
    (1 to n).map(_ => alphabet(Random.nextInt(alphabet.length))).mkString

  private def genPrice(): Int  = rand.between(1, 100)
  private def genDescription() = randStr(rand.between(5, 10))

  private var repo = Map.empty[String, ItemInfo]

  def getItem(sku: String): ItemInfo = {
    repo.get(sku) match {
      case Some(info) => info
      case None =>
        val newInfo = ItemInfo(genDescription(), genPrice())
        repo = repo.updated(sku, newInfo)
        newInfo
    }
  }
}
