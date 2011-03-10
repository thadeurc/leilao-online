package code.model.mundoj

import net.liftweb.mapper._
import net.liftweb.common.{Box, Empty}

object Item extends Item with LongKeyedMetaMapper[Item] with CRUDify[Long, Item]{
  override def fieldOrder = List(descricao)
  override def deleteMenuLoc = Empty
}

class Item extends LongKeyedMapper[Item] with IdPK {
  def getSingleton = Item
  object descricao extends MappedString(this, 100)

}