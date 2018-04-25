package driver;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class PerfectoHelper {

    // Setup Perfecto Credentials
    private static final String HOST = System.getenv("PERFECTO_CLOUD");
    private static final String USERNAME = System.getenv("PERFECTO_USERNAME");
    private static final String SECURITY_TOKEN = System.getenv("PERFECTO_SECURITY_TOKEN");
    private static final String remoteURL = "https://" + HOST + "/nexperience/perfectomobile/wd/hub/fast";

    private static RemoteWebDriver driver ;
    private static ReportiumClient reportiumClient ;
    private static Exception  reportException = null;

    public static RemoteWebDriver getDriver(){
        if (null == driver)
            return createDriver();
        else
            return driver;
    }
    public static ReportiumClient getReportiumClient(){
        if (null == reportiumClient)
            return createReportiumClient(getDriver());
        else
            return reportiumClient;
    }

    public static void setExceptionInformation(Exception ex){reportException = ex;}

    public static void setUp(){
        getDriver();
        getReportiumClient();
    }

    private static RemoteWebDriver createDriver(){
        try {

             DesiredCapabilities capabilities = null;
            // Are we running on a mobile device?
            if (null != System.getenv("TARGET_EXECUTION") && System.getenv("TARGET_EXECUTION").toLowerCase().equals("desktop")){
                capabilities = new DesiredCapabilities();
                setCapIfNotNull(capabilities, "browserName", "BROWSER");
                setCapIfNotNull(capabilities, "browserVersion", "BROWSER_VERSION");
                capabilities.setCapability("platformName", System.getenv("PLATFORM"));
            }
            else { // it's a mobile device
                String browserName = "mobileOS";
                capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
                setCapIfNotNull(capabilities, "manufacturer", "MANUFACTURER");
                setCapIfNotNull(capabilities, "model", "MODEL");
                setCapIfNotNull(capabilities, "network", "NETWORK");
                setCapIfNotNull(capabilities, "description", "DESCRIPTION");
// You can select the specific device by ID for the test, although this is not a best practice: better to select a device by its OS, model, version etc.
                setCapIfNotNull(capabilities, "deviceName","DEVICE_NAME");

            }


            // Setup Perfecto Credentials
            capabilities.setCapability("user", USERNAME);
            capabilities.setCapability("securityToken", SECURITY_TOKEN);

            // platform details
            capabilities.setCapability("platformVersion", System.getenv("PLATFORM_VERSION"));

            setCapIfNotNull(capabilities, "location", "LOCATION");
            setCapIfNotNull(capabilities, "resolution", "RESOLUTION");

            System.out.println(System.lineSeparator() + "caps: "+ capabilities.toString()+ System.lineSeparator());


            // Optional: browser logging
/*
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
            logPrefs.enable(LogType.BROWSER, Level.ALL);

            capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
*/


            driver = new RemoteWebDriver(new URL(remoteURL), capabilities);
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            if (null != System.getenv("TARGET_EXECUTION") && System.getenv("TARGET_EXECUTION").toLowerCase().equals("desktop"))
                driver.manage().window().maximize();
        } catch (MalformedURLException e) {

            System.out.println(e.toString());

        }catch (Exception e) {

            System.out.println(e.toString());
        }
        return driver;

    }
    private static void setCapIfNotNull(DesiredCapabilities caps, String capName, String definitionName){
        if (null != System.getenv(definitionName))
            caps.setCapability(capName, System.getenv(definitionName));
    }

    public static void tearDown(){
        if (null == reportException)
            reportiumClient.testStop(TestResultFactory.createSuccess());
        else
            reportiumClient.testStop(TestResultFactory.createFailure(reportException.toString(), reportException));
        // Retrieve the URL to the DigitalZoom Report (= Reportium Application) for an aggregated view over the execution
        String reportURL = reportiumClient.getReportUrl();
        System.out.println(System.lineSeparator()+ "Report URL: "+ reportURL+ System.lineSeparator());

        // avoid exceptions
        if (null != driver) {
            driver.close();
            driver.quit();
            driver = null;
        }
        reportiumClient = null;
        reportException = null;

    }

    private static ReportiumClient createReportiumClient(RemoteWebDriver driver) {
        try {
            // Reporting client. For more details, see http://developers.perfectomobile.com/display/PD/Reporting
            PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                    .withProject(new Project("My Project", "1.0"))
                    .withJob(new Job("My Job", 45))
                    .withContextTags("tag1")
                    .withWebDriver(driver)
                    .build();
            reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);


        }catch (Exception e) {

            System.out.println(e.toString());
        }
        return reportiumClient;
    }

}
