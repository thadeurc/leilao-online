package code.model.mundoj

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.common._


object Usuario extends Usuario with MetaMegaProtoUser[Usuario] {
  override def dbTableName = "users" // define the DB table name
  override def screenWrap = Full(<lift:surround with="default" at="content">
			       <lift:bind /></lift:surround>)
  override def fieldOrder = List(id, firstName, lastName, email, locale, timezone, password, textArea)
  override def skipEmailValidation = true
}

class Usuario extends MegaProtoUser[Usuario] {
  def getSingleton = Usuario
  object textArea extends MappedTextarea(this, 2048) {
    override def textareaRows  = 10
    override def textareaCols = 50
    override def displayName = "Informação Pessoal"
  }
  override def firstNameDisplayName = "Nome"
  override def lastNameDisplayName = "Sobrenome"
  override def timezoneDisplayName = "Fuso Horário"
  override def localeDisplayName = "Local"
  override def passwordDisplayName = "Senha"
  override def emailDisplayName = "Correio Eletrônico"
}

