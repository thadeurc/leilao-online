package code.model.mundoj

import net.liftweb.common._
import net.liftweb.sitemap.Loc._
import net.liftweb.http.RedirectResponse
import net.liftweb.sitemap.{Menu, Loc}
import net.liftweb.sitemap.Loc._
import java.util.Date
import net.liftweb.mapper._
import scala.xml._

object Item extends Item with LongKeyedMetaMapper[Item] with CRUDify[Long, Item]{
  override def fieldOrder = List(descricao)
  override def calcPrefix = List("Item")
  override def createMenuName = "Novo Item"
  override def showAllMenuName = "Leilões Ativos"
  override def viewMenuName = "Ver Item"
  override def deleteMenuName = "Apagar Item"
  override def editMenuName = "Editar Item"

  override def addlMenuLocParams: List[Loc.AnyLocParam] = {
    List(usuarioLogado)
  }

  private def usuarioLogado = If(() => Usuario.loggedIn_?, () => RedirectResponse("/user_mgt/login"))

  override def menus = {
    super.menus :::
    List(Menu(Loc("item-detalhe", List("item","detalhe") -> false, "Detalhes", Hidden)),
    Menu(Loc("item-terminado", List("item","terminados") -> false, "Leilões Terminados", usuarioLogado)))
  }

  override def findForListParams = {
    By_>(termino, new Date) :: OrderBy(termino, Descending) :: Nil
  }

  override def rowsPerPage = 10

  def lanceMaisAlto(itemId: Long): Box[Lance] = {
    Lance.lanceMaisAlto(itemId)
  }

  def terminados(primeiro: Long): List[Item] =  {
    findAll(By_<(termino, new Date),
            StartAt[Item](primeiro),
            MaxRows[Item](rowsPerPage),
            OrderBy(termino, Descending)
    )
  }
}

class Item extends LongKeyedMapper[Item] with IdPK {
  def getSingleton = Item
  object descricao extends MappedString(this, 100){
    override def displayName = "Item sob Leilão"
  }
  object termino extends MappedDateTime(this) {
    import java.util.Date
    import java.text.{SimpleDateFormat, ParseException}

    val formatador = new SimpleDateFormat("dd/MM/yyyy")

    override def displayName = "Termina em"

    override def parse(datetime: String): Box[Date] = {
      try {
        Full(formatador.parse(datetime))
      } catch {
        case e: ParseException => Empty
      }
    }

    override def asHtml = {
      Text(asString)
    }

    override def asString = {
      formatador.format(is)
    }
  }
}