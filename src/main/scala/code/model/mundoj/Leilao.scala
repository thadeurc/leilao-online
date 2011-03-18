package code.model.mundoj

import java.util.Date
import net.liftweb.common._
import code.lib.DependencyFactory
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import net.liftweb.mapper._
import net.liftweb.http.RedirectResponse

object Leilao extends Leilao with LongKeyedMetaMapper[Leilao] {

  lazy val date: Box[Date] = DependencyFactory.inject[Date]

  val detalhe = "/Leilao/detalhe/"
  val novo    = "/Leilao/criar/item/"

  override def fieldOrder = List(termino)

  def findActiveByItemId(itemId: Long): Box[Leilao] = {
    find(By(Leilao.item, id), By_>(termino,date.openOr(new Date)))
  }

  def disponivel = {
    If(() => Usuario.loggedIn_?, () => RedirectResponse("/login"))
  }

  def menus = {
    List(Menu(Loc("leilao-criar", List("Leilao","criar") -> true, "Novo", Hidden, disponivel)),
         Menu(Loc("leilao-detalhe",List("Leilao","detalhe") -> true, "Detalhe", Hidden, disponivel)),
         Menu(Loc("leilao-lista",List("Leilao","list") -> false, "Listar Leilões", disponivel))
    )
  }

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