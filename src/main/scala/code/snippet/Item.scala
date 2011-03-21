package code.snippet

import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import code.lib._
import Helpers._
import net.liftweb.http.S
import net.liftweb.http._
import net.liftweb.http.SHtml._
import code.model.mundoj.{Item => ItemData, Lance => LanceData, Usuario}
import xml.{NodeSeq, Text}


class Item {

  object valor extends RequestVar[String]("")
  object itemId extends RequestVar[String](S.param("id").openOr(""))

  def lance (in: NodeSeq): NodeSeq = {
    bind("entry", in, "itemid"-> SHtml.text(itemId.is, itemId(_), "type" -> "hidden"),
                      "valor" -> SHtml.text(valor.is, valor(_)),
                      "submit"-> SHtml.submit("Dar Lance!", novoLance))
  }

  def detalhes(in: NodeSeq): NodeSeq = {
    val id = S.param("id").map(toLong).openOr(-1L)
    ItemData.findByKey(id).map {
      item => bind("item", in, "descricao"        -> Text(item.descricao.is),
                               "lance_mais_alto"  -> lanceMaisAlto(id)
              )

    }.openOr{
      bind("item", in, "descricao" -> Text("Item %d não existe.".format(id)))
    }
  }

  def terminados (in: NodeSeq): NodeSeq = {
    val inicio = S.param("inicio").map(toLong).openOr(0L)

    def anterior(in: NodeSeq) = {
      if (inicio < ItemData.rowsPerPage) <xml:group> </xml:group>
      else <a href={"/Item/terminados?inicio=" + (0L max (inicio - 20L))}>{in}</a>
    }

    val leiloes = ItemData.terminados(inicio) match {
      case Nil => <tr><td colspan="3">Não há leilões terminados.</td></tr>
      case itens => itens.flatMap{
        item =>
          bind("linha", linha,
                     "descricao"-> Text(item.descricao.is),
                     "valor"    -> lanceMaisAlto(item.id.is),
                     "termino"  -> item.termino.asHtml)
      }
    }

    def proximo(in: NodeSeq) = {
      if (leiloes.length < ItemData.rowsPerPage) <xml:group> </xml:group>
      else <a href={"/Item/terminados?inicio="+(inicio + ItemData.rowsPerPage)}>{in}</a>
    }

    bind("tabela", in, "linhas"   -> leiloes,
                       "proximo"  -> proximo _,
                       "anterior" -> anterior _
    )
  }

  def linha = {
    <tr><td><linha:descricao/></td><td><linha:valor/></td><td><linha:termino/></td></tr>
  }

  def novoLance() {
    val valorEntrado = valor.is.toDouble
    if(valorEntrado <= 0.0){
      S.error("Valor deve ser positivo.")
    }else{
      val lance = LanceData.create
      lance.item(ItemData.findByKey(itemId.is.toLong))
      lance.valor(valorEntrado)
      lance.usuario(Usuario.currentUser)
      lance.validate match {
        case Nil => lance.save
        case x => S.error(x)
      }
      valor("")
    }
  }


  def lanceMaisAlto(itemId: Long): Text = {
    LanceData.lanceMaisAlto(itemId) match {
      case Full(info) => Text("R$" + info.valor)
      case _ => Text("Não há lances para esse item.")
    }

  }
}