package controllers

import javax.inject.Inject

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats.doubleFormat
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}
import services.AlertService

import scala.util._

class AlertController @Inject() (alertService: AlertService) extends Controller {
  def index = Action { request => Ok(s"Welcome.") }

  def getAllAlerts = Action {Ok(Json.toJson(alertService.getAllAlerts)) }

  case class AlertData(email: String, prix_min: Option[Double], prix_max: Option[Double],
                       surface_min: Option[Double], surface_max: Option[Double],
                       code_type_bien: Option[String], frequence: String,
                       nbre_min_piece: Option[Int], nbre_max_piece: Option[Int],
                       transaction: String, ville: Option[String], pays: Option[String],
                       dept: Option[String], codeApplication: Option[Int],
                       codeAncien: Option[String], programmeNeuf: Option[Boolean],
                       origineCobranding: Option[String], cleEncode: Option[String],
                       nomcomplet: Option[String])

  private val alertForm = Form(mapping("email" -> text, "prix_min" -> optional(of(doubleFormat)),
    "prix_max" -> optional(of(doubleFormat)), "surface_min" -> optional(of(doubleFormat)),
    "surface_max" -> optional(of(doubleFormat)), "code_type_bien" -> optional(text),
    "frequence" -> text, "nbre_min_piece" -> optional(number),
    "nbre_max_piece" -> optional(number),
    "transaction" -> text, "ville" -> optional(text),
    "pays" -> optional(text), "dept" -> optional(text),
    "codeApplication" -> optional(number), "codeAncien" -> optional(text),
    "programmeNeuf" -> optional(boolean), "origineCobranding" -> optional(text),
    "cleEncode" -> optional(text), "nomcomplet" -> optional(text))
  (AlertData.apply)(AlertData.unapply))

  def createAlert = Action(parse.form(alertForm)) { implicit request =>
    val alertData: AlertData = request.body
    alertService.createAlert(alertData.email,
      alertData.prix_min,
      alertData.prix_max,
      alertData.surface_min,
      alertData.surface_max,
      alertData.code_type_bien,
      alertData.frequence,
      alertData.nbre_min_piece,
      alertData.nbre_max_piece,
      alertData.transaction,
      alertData.ville,
      alertData.pays,
      alertData.dept,
      alertData.codeApplication,
      alertData.codeAncien,
      alertData.programmeNeuf,
      alertData.origineCobranding,
      alertData.cleEncode,
      alertData.nomcomplet)
    Ok("{}")
  }

  def findAlertByEmail(email: String): Action[AnyContent] = Action {
    alertService.findAlertByEmail(email) match{
      case Nil => NotFound("")
      case l :: Nil => Ok(Json.toJson(l))
    }
  }

  def findAlertById(id: String) = Action {
    alertService.findAlertById(id) match {
      case Some(alert) => Ok(Json.toJson(alert))
      case None => NotFound("")
    }
  }

  def deleteAlertById(id: String) = Action {
      alertService.deleteAlertById(id) match {
      case Failure(_) => NotFound("")
      case Success(_) => Ok("")
    }
  }

  def deleteAlertByEmail(email: String): Action[AnyContent] = Action {
    alertService.deleteAlertByEmail(email) match{
      case Failure(_) => NotFound("")
      case Success(_) => Ok("")
    }
  }
}
