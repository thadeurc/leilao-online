package code.model.mundoj

import net.liftweb.mapper._
import net.liftweb.common._
import net.liftweb.sitemap.Loc
import net.liftweb.sitemap.Loc._
import net.liftweb.http.RedirectResponse

object Item extends Item with LongKeyedMetaMapper[Item] with CRUDify[Long, Item]{
  override def fieldOrder = List(descricao)
  override def calcPrefix = List("Item")
  override def createMenuName = "Criar Novo Item"
  override def showAllMenuName = "Listar Items"
  override def viewMenuName = "Ver Item"
  override def deleteMenuName = "Apagar Item"
  override def editMenuName = "Editar Item"

  override def addlMenuLocParams: List[Loc.AnyLocParam] = {
    List(If(() => Usuario.loggedIn_?, () => RedirectResponse("/login")))
  }

}

class Item extends LongKeyedMapper[Item] with IdPK {
  def getSingleton = Item
  object descricao extends MappedString(this, 100){
    override def displayName = "Descrição do item"
  }

}