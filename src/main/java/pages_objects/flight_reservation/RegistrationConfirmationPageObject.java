package pages_objects.flight_reservation;

import pages_objects.BasePageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegistrationConfirmationPageObject extends BasePageObject {

    @FindBy(id = "go-to-flights-search")
    private WebElement goToFlightsSearchButton;

    @FindBy(css = "#registration-confirmation-section p b")
    private WebElement firstNameElement;

    public RegistrationConfirmationPageObject(WebDriver driver){
        super(driver);
    }

    @Override
    public boolean hasLoaded() {
        this.wait.until(ExpectedConditions.visibilityOf(this.goToFlightsSearchButton));
        return this.goToFlightsSearchButton.isDisplayed();
    }

    public String getFirstName(){
        return this.firstNameElement.getText();
    }

    public void goToFlightsSearch(){
        this.goToFlightsSearchButton.click();
    }

}
