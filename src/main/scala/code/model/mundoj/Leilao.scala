package code.model.mundoj

import net.liftweb.mapper._

object Leilao extends Leilao with LongKeyedMetaMapper[Leilao] {
  override def fieldOrder = List(termino)
}

class Leilao extends LongKeyedMapper[Leilao] with IdPK {
  def getSingleton = Leilao
  object descricao extends MappedString(this, 100){
    override def displayName = "Descrição do Leilão"
  }
  object termino extends MappedDateTime(this) {
    override def displayName = "Data de término do Leilão"
  }
  object item extends MappedLongForeignKey(this, Item)
}