//package models
//
//import org.scalatest._
//import org.scalatestplus.play._
//import play.api.Application
//import play.api.db.evolutions.{Evolution, Evolutions, SimpleEvolutionsReader}
//import play.api.db.{Database, Databases}
//import play.api.inject.guice.GuiceApplicationBuilder
//import services.AlertService
//
//object Test_ {
//  val PG_DRIVER = "org.postgresql.Driver"
//  val PG_URL = "jdbc:postgresql://localhost:5432/postgres"
//  val PG_URLTest = "jdbc:postgresql://localhost:5432/test"
//  val PG_USERNAME = "postgres"
//  val PG_PASSWORD = "postgres"
//
//  val dropDBtest = """DROP DATABASE IF EXISTS test"""
//  val createDBtest = """CREATE DATABASE test"""
//  val dropTableAlerts = """DROP TABLE IF EXISTS alerts"""
//  val createTableAlerts =
//    """create table if not exists alerts(id varchar not null PRIMARY KEY,
//         email varchar not null,
//         prix_min float,
//         prix_max float,
//         surface_min float,
//         surface_max float,
//         code_type_bien varchar,
//         frequence varchar not null,
//         nbre_min_piece integer,
//         nbre_max_piece integer,
//         transaction varchar not null,
//         ville varchar,
//         pays varchar,
//         dept varchar,
//         codeApplication integer,
//         codeAncien varchar,
//         programmeNeuf boolean,
//         origineCobranding varchar,
//         cleEncode varchar,
//         nomcomplet varchar,
//         date_creation date not null,
//         date_fin_alerte date not null)"""
//}
//
//trait TestAPI extends PlaySpec with BeforeAndAfterEach with BeforeAndAfterAll{
//  import Test_._
//
//  /**Useless actually*/
//  //  lazy val appConfigTest: AppConfigTest = null
//  //  lazy val xa = DriverManagerTransactor[IOLite](
//  //    "org.postgresql.Driver", appConfigTest.dbUrl, appConfigTest.dbUsername, appConfigTest.dbPassword
//  //  )
//
//  lazy val dao = new DAO()
//  lazy val alertService = new AlertService(dao)
//
//  val databaseTest: Database = Databases(
//    driver = PG_DRIVER,
//    url = PG_URL,
//    config = Map(
//      "username" -> PG_USERNAME,
//      "password" -> PG_PASSWORD
//    ))
//
//  val app: Application = new GuiceApplicationBuilder()
//    .configure(Map(
//      "db.default.url" -> sys.env.getOrElse(
//        PG_DRIVER, PG_URL),
//      "db.default.username" -> PG_USERNAME,
//      "db.default.password" -> PG_PASSWORD
//    ))
//    .build
//
//  override def beforeAll() {
//    println("ICI")
//    databaseTest.getConnection.createStatement.executeUpdate(dropDBtest)
//    println("connect to postgres and drop if exists db test")
//    databaseTest.getConnection.createStatement.executeUpdate(createDBtest)
//    println("create bd test")
//    lazy val data: Database = Databases.inMemory(name = "test")
//    data.getConnection
//    println("connect to dbtest")
//  }
//
//  override def beforeEach() {
//    val db = app.injector.instanceOf[Database]
//    Evolutions.applyEvolutions(db, SimpleEvolutionsReader.forDefault(Evolution(
//      999, createTableAlerts, dropTableAlerts
//    )))
//  }
//
//  override def afterEach {
//    val db = app.injector.instanceOf[Database]
//    Evolutions.cleanupEvolutions(db)
//  }
//
//  override def afterAll() {
//    Databases.inMemory(name = "test").shutdown()
//    println("shutdown db test")
//    databaseTest.getConnection.createStatement.executeUpdate(dropDBtest)
//    println("drop db test")
//    databaseTest.shutdown()
//    println("shutdown postgres")
//  }
//}
//
//
//class TestSpec extends TestAPI {
//  "createAlert" should {
//    "create something. DB not empty " in {
//      val dbStatus: Seq[Alert] = alertService.getAllAlerts
//      val createTest = alertService.createAlert("test@psql.com", None, None, None, None, None, "D", None, None, "Louer", None, None, None, None, None, None, None, None, None)
//      val dbStatus2: Seq[Alert] = alertService.getAllAlerts
//      assert(dbStatus.size != dbStatus2.size)
//    }
//  }
//
//  "findAlertByEmail" should {
//    "not work" in {
//      val findTiti: Seq[Alert] = alertService.findAlertByEmail("doesNot@exist.com")
//      assert(findTiti == Seq())
//    }
//  }
//
//  "findAlertById" should {
//    "not work" in {
//      val findId5 = alertService.findAlertById("id5")
//      assert(findId5 == None)
//    }
//  }
//
//  "deleteAlertById" should {
//    "not work " in {
//      val deleteId = alertService.deleteAlertById("randId" )
//      assert(deleteId.isFailure)
//    }
//  }
//
//  "deleteAlertByEmail" should {
//    "not work " in {
//      val delAlerts = alertService.deleteAlertByEmail("tyty@gmail.com")
//      assert(delAlerts.isFailure)
//    }
//  }
//
//}