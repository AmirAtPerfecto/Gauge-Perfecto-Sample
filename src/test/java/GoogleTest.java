import com.perfecto.reportium.test.TestContext;
import com.thoughtworks.gauge.Step;
import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

public class GoogleTest {

    public void GoogleTest() {
        DriverFactory.getReportiumClient().testStart("Example Perfecto Gauge Google Test", new TestContext("Google", "Gauge"));
    }


    private void setTestResultFailed(Exception e){
        DriverFactory.setExceptionInformation(e);
    }

    @Step("On the homepage")
    public void navigateToHomePage() {
        try {
            DriverFactory.getDriver().get("https://www.google.com");
        } catch (Exception e) {
            setTestResultFailed(e);
            e.printStackTrace();
        }
    }

    @Step("Search for term <term>")
    public void searchFor(String term) {
        try {
            WebElement element = DriverFactory.getDriver().findElement(By.name("q"));
            element.sendKeys(term);
            element.submit();
        } catch (Exception e) {
            setTestResultFailed(e);
            e.printStackTrace();
        }
    }

    @Step("Make sure the page title is <term>")
    public void checkPageTitle(String term) {

        try {
            assertEquals(term, DriverFactory.getDriver().getTitle());
        } catch (Exception e) {
            setTestResultFailed(e);
            e.printStackTrace();
        }
    }
}
