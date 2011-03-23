package code.model.mundoj

import java.math.MathContext
import _root_.net.liftweb.mapper._
import net.liftweb.common.{Empty, Box, Full}

object Lance extends Lance with LongKeyedMetaMapper[Lance] {
  override def fieldOrder = List(id)

  def lanceMaisAlto(itemId: Long): Box[Lance] = {
    find(By(Lance.item, itemId), OrderBy(valor, Descending))
  }

}

class Lance extends LongKeyedMapper[Lance] with IdPK{
  import MathContext._
  def getSingleton = Lance
  object usuario extends MappedLongForeignKey(this, Usuario)
  object item extends MappedLongForeignKey(this, Item)
  object valor extends MappedDecimal(this, DECIMAL64, 2)
}