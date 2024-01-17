package tests.flight_reservation;

import tests.BaseTest;
import tests.flight_reservation.model.FlightReservationTestData;
import util.Config;
import util.Constants;
import util.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages_objects.flight_reservation.*;

public class FlightReservationTest extends BaseTest {
    private FlightReservationTestData testData;

    @BeforeTest
    @Parameters("testDataPath")
    public void setParameters(String testDataPath) {
        this.testData = JsonUtil.getTestData(testDataPath, FlightReservationTestData.class);
    }

    @Test
    public void userRegistrationTest() {
        RegistrationPageObject registrationPage = new RegistrationPageObject(driver);
        registrationPage.goTo(Config.get(Constants.FLIGHT_RESERVATION_URL));
        Assert.assertTrue(registrationPage.hasLoaded());

        registrationPage.enterUserDetails(testData.firstName(), testData.lastName());
        registrationPage.enterUserCredentials(testData.email(), testData.password());
        registrationPage.enterAddress(testData.street(), testData.city(), testData.zip());
        registrationPage.register();
    }

    @Test(dependsOnMethods = "userRegistrationTest")
    public void registrationConfirmationTest() {
        RegistrationConfirmationPageObject registrationConfirmationPage = new RegistrationConfirmationPageObject(driver);
        Assert.assertTrue(registrationConfirmationPage.hasLoaded());
        Assert.assertEquals(registrationConfirmationPage.getFirstName(), testData.firstName());
        registrationConfirmationPage.goToFlightsSearch();
    }

    @Test(dependsOnMethods = "registrationConfirmationTest")
    public void flightsSearchTest() {
        FlightsSearchPageObject flightsSearchPage = new FlightsSearchPageObject(driver);
        Assert.assertTrue(flightsSearchPage.hasLoaded());
        flightsSearchPage.selectPassengers(testData.passengersCount());
        flightsSearchPage.searchFlights();
    }

    @Test(dependsOnMethods = "flightsSearchTest")
    public void flightsSelectionTest() {
        FlightsSelectionPageObject flightsSelectionPage = new FlightsSelectionPageObject(driver);
        Assert.assertTrue(flightsSelectionPage.hasLoaded());
        flightsSelectionPage.selectFlights();
        flightsSelectionPage.confirmFlights();
    }

    @Test(dependsOnMethods = "flightsSelectionTest")
    public void flightReservationConfirmationTest() {
        FlightConfirmationPageObject flightConfirmationPage = new FlightConfirmationPageObject(driver);
        Assert.assertTrue(flightConfirmationPage.hasLoaded());
        Assert.assertEquals(flightConfirmationPage.getPrice(), testData.expectedPrice());
    }

}
