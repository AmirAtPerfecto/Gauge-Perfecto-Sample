import com.perfecto.reportium.test.TestContext;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class StepImplementation {

    public void StepImplementation() {
        DriverFactory.getReportiumClient().testStart("Example Perfecto Gauge Google Test", new TestContext("Google", "Gauge"));
    }

    private void setTestResultFailed(Exception e){
        DriverFactory.setExceptionInformation(e);
    }

    @Step("Go to Gauge Get Started Page")
  public void gotoGetStartedPage() throws InterruptedException {
      WebElement getStartedButton = DriverFactory.getDriver().findElement(By.xpath("//a[@class='link-get-started']"));
      getStartedButton.click();

      Gauge.writeMessage("Page title is %s", DriverFactory.getDriver().getTitle());
  }

  @Step("Ensure installation instructions are available")
  public void ensureInstallationInstructionsAreAvailable() throws InterruptedException {
      WebElement instructions = DriverFactory.getDriver().findElement(By.xpath("//p[@class='instruction']"));
      assertTrue(instructions!=null);
  }

  @Step("Open the Gauge homepage")
  public void implementation1() {
      String app_url = "https://gauge.org";
      DriverFactory.getDriver().get(app_url + "/");
      assertTrue(DriverFactory.getDriver().getTitle().contains("Gauge"));
  }
}
