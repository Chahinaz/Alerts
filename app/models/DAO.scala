package models

import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject
import conf.AppConfig
import doobie.imports._
import doobie.util.iolite.IOLite
import doobie.util.transactor.DriverManagerTransactor
import scala.util._
import org.joda.time._

class DAO @Inject() (appConfig: AppConfig) {
  lazy val gl = scala.concurrent.ExecutionContext.Implicits.global

  val xa = DriverManagerTransactor[IOLite](
    "org.postgresql.Driver", appConfig.dbUrl, appConfig.dbUsername, appConfig.dbPassword
  )

  implicit val DateTimeMeta: Meta[DateTime] =
    Meta[java.sql.Timestamp].nxmap(
      ts => new DateTime(ts.getTime()),
      dt => new java.sql.Timestamp(dt.getMillis) )

  def createAlert(alert: Alert): Try[Unit] = {
    val generatedKey =  Some(UUID.randomUUID().toString)

    val codeAncien = List("Ancien", "Recent", "Neuf", None)
    val freq = List("H", "J", "D")
    val trans = List("Acheter", "Louer")
    if (freq.contains(alert.frequence) && codeAncien.contains(alert.codeAncien) && trans.contains(alert.transaction)){
      val sql =
        sql"""INSERT INTO alerts
             values ($generatedKey, ${alert.email}, ${alert.prix_min},
             ${alert.prix_max},${alert.surface_min}, ${alert.surface_max},
             ${alert.code_type_bien}, ${alert.frequence}, ${alert.nbre_min_piece},
             ${alert.nbre_max_piece}, ${alert.transaction}, ${alert.ville},
             ${alert.pays}, ${alert.dept}, ${alert.codeApplication},
             ${alert.codeAncien}, ${alert.programmeNeuf}, ${alert.origineCobranding},
             ${alert.cleEncode}, ${alert.nomcomplet}, ${DateTime.now()}, ${DateTime.now().plusYears(10)})"""

      if (sql.update.run.transact(xa).unsafePerformIO == 1) Success(())
      else Failure(new Exception() )
    }
    else Failure(new Exception() )
  }

  def getAllAlerts: Seq[Alert] = {
    val sql = sql"""SELECT * FROM alerts GROUP BY alerts.id """.query[Alert]
    sql
      .list
      .transact(xa)
      .unsafePerformIO
  }

  def findAlertByEmail(email: String): Seq[Alert] ={
    val sql = sql"SELECT * FROM alerts WHERE alerts.email = $email".query[Alert]
    sql
      .list
      .transact(xa)
      .unsafePerformIO
  }

  def findAlertById(id: String): Option[Alert] ={
    val sql = sql"SELECT * FROM alerts WHERE alerts.id = $id".query[Alert]
    sql
      .option
      .transact(xa)
      .unsafePerformIO
  }

  def deleteAlertById(id: String): Try[Unit] = {
    val sql = sql"""DELETE FROM alerts WHERE alerts.id = ${id}"""
    if (sql.update.run.transact(xa).unsafePerformIO == 1) Success(())
    else Failure(new Exception())
  }

  def deleteAlertByEmail(email: String): Try[_] = {
    val sql = sql"""DELETE FROM alerts WHERE alerts.email = ${email}"""
    if (sql.update.run.transact(xa).unsafePerformIO == 1) Success(())
    else Failure(new Exception())
  }
}
