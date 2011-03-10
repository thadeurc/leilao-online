package code.model.mundoj

import net.liftweb.mapper.{LongKeyedMetaMapper, MappedString, IdPK, LongKeyedMapper}

object Item extends Item with LongKeyedMetaMapper[Item] {
  override def fieldOrder = List(descricao)
}

class Item extends LongKeyedMapper[Item] with IdPK {
  def getSingleton = Item
  object descricao extends MappedString(this, 100)
}