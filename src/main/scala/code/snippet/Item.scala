package code.snippet

import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import code.lib._
import Helpers._
import net.liftweb.http.S
import net.liftweb.http._
import code.model.mundoj.{Item => ItemData, Leilao => LeilaoData}
import xml.{Elem, NodeSeq, Text}


class Item {

  def detalhes(in: NodeSeq): NodeSeq = {
    val id = S.param("id").map(toLong).openOr(-1L)
    ItemData.findByKey(id).map {
      item => bind("item", in, "descricao" -> Text(item.descricao.is),
                               "leiloar"   -> link(id))

    }.openOr{
      bind("item", in, "descricao" -> Text("Item %d não existe.".format(id)),
                       "leiloar"   -> Text(""))
    }
  }

  private def link(itemId: Long): Elem = {
    ItemData.leilao(itemId) match {
      case Full(leilao) => <a href={LeilaoData.detalhe + leilao.id.is}>Ir para o leilão corrente</a>
      case            _ => <a href={LeilaoData.novo + itemId}>Leiloar</a>
    }
  }
}