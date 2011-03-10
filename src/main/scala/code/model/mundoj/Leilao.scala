package code.model.mundoj

import net.liftweb.mapper._

object Leilao extends Leilao with LongKeyedMetaMapper[Leilao] with CRUDify[Long, Leilao]{
  override def fieldOrder = List(termino)
}

class Leilao extends LongKeyedMapper[Leilao] with IdPK {
  def getSingleton = Leilao
  object descricao extends MappedString(this, 100)
  object termino extends MappedDateTime(this)
  object item extends MappedLongForeignKey(this, Item)
}