import com.thoughtworks.gauge.Step;
import driver.ApplitoolsHelper;
import driver.DriverFactory;

public class ApplitoolsImplementation {

    @Step("I check window <term>")
    public static void checkWindow(String tag) {ApplitoolsHelper.checkWindow(tag, false, DriverFactory.getDriver());}


    @Step("I check complete window <term>")
    public static void checkCompleteWindow(String tag) {ApplitoolsHelper.checkWindow(tag, true, DriverFactory.getDriver());}



}
