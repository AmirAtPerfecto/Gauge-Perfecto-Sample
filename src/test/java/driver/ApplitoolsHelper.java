package driver;

import com.applitools.eyes.TestResults;
import com.applitools.eyes.exceptions.DiffsFoundException;
import com.applitools.eyes.selenium.Eyes;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ApplitoolsHelper {


    private static Eyes eyes ;

    public static Eyes getEyes(){
        if (null == eyes)
            return createEyes();
        else
            return eyes;
    }
    public static void setUp(){
        getEyes();
    }

    public static void tearDown(){
        tearDownEyes();
        eyes = null;
    }

    private static Eyes createEyes(){

        try {
            System.out.print(">>>>>> Starting Eyes driver <<<<<<<<<");
            String applitoolsAPIKey = System.getenv("APPLITOOLS_KEY");
            if (null != applitoolsAPIKey) {
                eyes = new Eyes();
                eyes.setApiKey(applitoolsAPIKey);
                return eyes;
            }

        }catch (Exception e) {

            System.out.println(e.toString());
        }
        return null;

    }

    private static void tearDownEyes(){
        System.out.print(System.lineSeparator()+ ">>>>>> END <<<<<<<<<\n");
        if (null != eyes) {
            TestResults close = null;
            try {
                close = eyes.close();
                System.out.println(">>>>>> Applitools report URL: "+ close.getUrl());
            } catch (DiffsFoundException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void checkWindow(String tag, boolean fullPageScreenshot, RemoteWebDriver driver) {
        if (null != eyes) {
            if (!eyes.getIsOpen())
                try {
                    eyes.open(driver, "Gauge", tag);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            try {
                if (fullPageScreenshot)
                    eyes.setForceFullPageScreenshot(true);
                eyes.checkWindow(tag);
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }



}
