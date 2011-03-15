package code.snippet

import code.model.mundoj.Usuario
import _root_.scala.xml.{NodeSeq, Text}
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import code.lib._
import Helpers._

class Welcome {
  def message(in: NodeSeq): NodeSeq = {
    if(Usuario.loggedIn_?) Text("Olá, %s! Seja Bem Vindo!".format(Usuario.currentUser.open_!.niceName))
    else Text("Seja Bem Vindo. Para que você possa fazer uso da aplicação é necessário estar logado.")
  }
}