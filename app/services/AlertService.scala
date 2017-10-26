package services

import javax.inject.Inject

import models._

import scala.util._

case class AlertService @Inject() (dao: DAO){

  def createAlert(email: String, prix_min: Option[Double],
                  prix_max: Option[Double], surface_min: Option[Double],
                  surface_max: Option[Double], code_type_bien: Option[String],
                  frequence: String, nbre_min_piece: Option[Int],
                  nbre_max_piece: Option[Int], transaction: String, ville: Option[String],
                  pays: Option[String], dept: Option[String],
                  codeApplication: Option[Int], codeAncien: Option[String],
                  programmeNeuf: Option[Boolean], origineCobranding: Option[String],
                  cleEncode: Option[String], nomcomplet: Option[String]): Try[_] = {
    val alert = Alert(
      email = email,
      prix_min = prix_min,
      prix_max = prix_max,
      surface_min = surface_min,
      surface_max = surface_max,
      code_type_bien = code_type_bien,
      frequence = frequence,
      nbre_min_piece = nbre_min_piece,
      nbre_max_piece = nbre_max_piece,
      transaction = transaction,
      ville = ville,
      pays = pays,
      dept = dept,
      codeApplication = codeApplication,
      codeAncien = codeAncien,
      programmeNeuf = programmeNeuf,
      origineCobranding = origineCobranding,
      cleEncode = cleEncode,
      nomcomplet = nomcomplet
    )
    dao.createAlert(alert)
  }

  def getAllAlerts: Seq[Alert] = dao.getAllAlerts
  def findAlertByEmail(email: String): Seq[Alert] = dao.findAlertByEmail(email)
  def findAlertById(id: String): Option[Alert] = dao.findAlertById(id)
  def deleteAlertById(id: String): Try[_] = dao.deleteAlertById(id)
  def deleteAlertByEmail(email: String): Try[_] = dao.deleteAlertByEmail(email)
}



