import com.perfecto.reportium.test.TestContext;
import com.thoughtworks.gauge.Step;
import driver.DriverFactory;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.Map;

public class iOSCalculator {

    public void iOSCalculator() {
        DriverFactory.getReportiumClient().testStart("Example Perfecto Gauge iOS Calculator", new TestContext("iOS Calculator", "Gauge"));
    }


    private void setTestResultFailed(Exception e){
        DriverFactory.setExceptionInformation(e);
    }

    @Step("Open the calculator")
    public void launchCalculator() {
        try {
            launchApp(DriverFactory.getDriver(), "Calculator");
        } catch (Exception e) {
            setTestResultFailed(e);
            e.printStackTrace();
        }
    }


    @Step("I press <term>")
    public void clickElement(String term) {
        try {
            switchToContext(DriverFactory.getDriver(), "NATIVE_APP");
//            driver.findElementByXPath("//*[@label=\"clear\"]").click();
//            driver.findElementByXPath("//*[@label=\"1\"]").click();

            DriverFactory.getDriver().findElementByXPath("//*[@label=\""+term+"\"]").click();
        } catch (Exception e) {
            setTestResultFailed(e);
            e.printStackTrace();
        }
    }

    @Step("The result should be <term>")
    public void validateResult(String term) {

        try {
            switchToContext(DriverFactory.getDriver(), "NATIVE_APP");
            DriverFactory.getDriver().findElementByXPath("//*[@label=\""+term+"\"]");
        } catch (Exception e) {
            setTestResultFailed(e);
            e.printStackTrace();
        }
    }

    // ****==========  Specific perfecto utilities   =============****//
    //Launches application
    private static String launchApp(RemoteWebDriver driver, String app) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", app);
        return (String) driver.executeScript("mobile:application:open", params);
    }
    private static void switchToContext(RemoteWebDriver driver, String context) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", context);
        executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
    }

}
