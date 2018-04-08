package driver;

import com.perfecto.reportium.client.ReportiumClient;
import com.thoughtworks.gauge.AfterSpec;
import com.thoughtworks.gauge.BeforeSpec;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

    @BeforeSpec
    //@BeforeSuite
    public void setUp() {
        PerfectoHelper.setUp();

    }


    //@AfterSuite
    @AfterSpec
    public void tearDown() {
        PerfectoHelper.tearDown();
    }

    public static RemoteWebDriver getDriver(){return PerfectoHelper.getDriver();}
    public static ReportiumClient getReportiumClient(){return PerfectoHelper.getReportiumClient();}
    public static void setExceptionInformation(Exception ex){PerfectoHelper.setExceptionInformation(ex);}
 }
