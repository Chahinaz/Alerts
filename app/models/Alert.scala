package models

import play.api.libs.json.Json

case class Alert(id: Option[String] = None,
                 email: String,
                 prix_min: Option[Double],
                 prix_max: Option[Double],
                 surface_min : Option[Double],
                 surface_max : Option[Double],
                 code_type_bien: Option[String],
                 frequence: String,
                 nbre_min_piece: Option[Int],
                 nbre_max_piece: Option[Int],
                 transaction: String,
                 ville: Option[String],
                 pays: Option[String],
                 dept: Option[String],
                 codeApplication: Option[Int],
                 codeAncien: Option[String],
                 programmeNeuf: Option[Boolean],
                 origineCobranding : Option[String],
                 cleEncode : Option[String],
                 nomcomplet : Option[String]
)

object Alert {
  implicit val alerteFormat = Json.format[Alert]
}


