package models

import org.mockito.Mockito._
import org.scalatest.mock._
import org.scalatestplus.play._
import services._

import scala.util._

class AlertSpec extends PlaySpec with MockitoSugar {

  val alertDAO = mock[DAO]
  val mockAlertService = new AlertService(alertDAO)

  "createAlert" should {
    " succeed" in {
      when(alertDAO.createAlert(Alert(
        email = "toto@gmail.com",
        prix_min = null,
        prix_max = null,
        surface_min = null,
        surface_max = null,
        code_type_bien = null,
        frequence = "J",
        nbre_min_piece = null,
        nbre_max_piece = null,
        transaction = "Acheter",
        ville = null,
        pays = null,
        dept = null,
        codeApplication = null,
        codeAncien = null,
        programmeNeuf = null,
        origineCobranding = null,
        cleEncode = null,
        nomcomplet = null))) thenReturn Success(())

      val actualResult = mockAlertService.createAlert(
        email = "toto@gmail.com",
        prix_min = null,
        prix_max = null,
        surface_min = null,
        surface_max = null,
        code_type_bien = null,
        frequence = "J",
        nbre_min_piece = null,
        nbre_max_piece = null,
        transaction = "Acheter",
        ville = null,
        pays = null,
        dept = null,
        codeApplication = null,
        codeAncien = null,
        programmeNeuf = null,
        origineCobranding = null,
        cleEncode = null,
        nomcomplet = null)
      assert(actualResult.isSuccess === true)
    }
    "fail" in {
      when(alertDAO.createAlert(Alert(
        email = "toto@gmail.com",
        prix_min = null,
        prix_max = null,
        surface_min = null,
        surface_max = null,
        code_type_bien = null,
        frequence = "K",
        nbre_min_piece = null,
        nbre_max_piece = null,
        transaction = null, //error
        ville = null,
        pays = null,
        dept = null,
        codeApplication = null,
        codeAncien = null,
        programmeNeuf = null,
        origineCobranding = null,
        cleEncode = null,
        nomcomplet = null))) thenReturn Failure(new Exception)

      val actualResult = mockAlertService.createAlert(
        "toto@gmail.com",
        null, null, null, null,
        null,
        "ugxoznefd", //error
        null, null,
        "Vente",
        null, null, null,
        null, null,
        null,
        null, null, null)
      assert(actualResult.eq(null) === true)
    }


    "getAllAlerts " should {
      " be empty" in {
        when(alertDAO.getAllAlerts) thenReturn (List.empty)
        val actual = mockAlertService.getAllAlerts
        assert(actual.isEmpty)
      }
    }

    "deleteAlertById => alertid4 " should {
      " succeed" in {
        when(alertDAO.deleteAlertById("0d4da78e-5657-4f3d-acaa-67172f730497")) thenReturn Success(())
        val actual = mockAlertService.deleteAlertById("0d4da78e-5657-4f3d-acaa-67172f730497")
        assert(actual.isSuccess === true)
      }
    }

    "deleteAlertByEmail => toto@test.com" should {
      " succeed" in {
        when(alertDAO.deleteAlertByEmail("toto@test.com")) thenReturn Failure(new Exception)
        val actual = mockAlertService.deleteAlertByEmail("toto@test.com")
        assert(actual.isFailure === true)
      }
    }

    "findAlertByEmail => toto@test.com " should {
      " be an empty list" in {
        when(alertDAO.findAlertByEmail("toto@test.com")) thenReturn (List.empty)
        val actual = mockAlertService.findAlertByEmail("toto@test.com")
        assert(actual.isEmpty)
      }
    }

    "findAlertById => alertid2" should {
      " not work" in {
        when(alertDAO.findAlertById("alertid2")) thenReturn None
        val actual = mockAlertService.findAlertById("alertid2")
        assert(actual.isEmpty)
      }
    }
  }
}


