import axe.AccessibilityException;
import axe.AxeHelper;
import axe.AxeTestResult;
import com.thoughtworks.gauge.Step;
import driver.DriverFactory;
import driver.PerfectoHelper;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AXEImplementation {

    @Step("I check window for accessibility <term>")
    public void axe_scan(String pageName){

        PerfectoHelper.getReportiumClient().stepStart("AXE scan for: "+ pageName);
        DriverFactory.getDriver().getScreenshotAs(OutputType.BASE64);
        AxeHelper axe = new AxeHelper(DriverFactory.getDriver());
        axe.runAxe();

        axe.startHighlighter("violations");
        List<AxeTestResult> violations = null;
        try {
            violations = axe.axeEverything().violations;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (null != violations && 0 < violations.size()){
            int objects = 0;
            for (AxeTestResult t:violations) { objects = objects + t.nodes.size();}
            String s = System.lineSeparator() + "Accessibility scan found "+ violations.size() + " violations in " + objects + " objects";
            System.out.println(s);
            perfectoComment(s);
        }
        final StringBuilder errors = new StringBuilder();
        int violationsCount = 0;
        int errorCount = 0;
        while (true) {
            final Map<String, ?> violation = axe.nextHighlight();
            if (violation == null) {
                break;
            }
            errorCount++;
            final String ruleId = (String) violation.get("issue");
            if (0 != ruleId.compareToIgnoreCase(violations.get(violationsCount).id))
                violationsCount++;

            final Map<String, String> node = (Map<String, String>) violation.get("node");

            final String impact = node.get("impact");
            final String summary = node.get("failureSummary");
            final String html = node.get("html");
            final String selector = (String) violation.get("target");

            final String message = String.format("%s - %s%n %s%n Selector:\t%s%n HTML:\t\t%s%n%n",
                    impact, ruleId, summary, selector, html);
            final String perfectoReportMessage = String.format("Impact: %s;;; Rule ID: %s;;; Summary: %s;;; Selector:\t%s HTML:\t\t%s",
                    impact, ruleId, summary, selector, html);
            try {
                final String perfectoReportMessageComplete = String.format("%s;;; help: %s;;; helpURL: %s;;; Tags: %s",
                        perfectoReportMessage,
                        violations.get(violationsCount).help,
                        violations.get(violationsCount).helpUrl,
                        violations.get(violationsCount).tags.toString());

                perfectoComment(perfectoReportMessageComplete.replace(";;;",System.lineSeparator()));
                System.out.println("Error "+ errorCount +System.lineSeparator()+perfectoReportMessageComplete.replace(";;;",System.lineSeparator()));
            } catch (Exception e) {
                e.printStackTrace();
                perfectoComment(perfectoReportMessage);
            }

            DriverFactory.getDriver().getScreenshotAs(OutputType.BASE64);
            errors.append(message);

        }

        if (errorCount > 0) {
            final Capabilities capabilities = DriverFactory.getDriver().getCapabilities();
            final String platform = String.valueOf(capabilities.getCapability("platformName"));
            final String version = String.valueOf(capabilities.getCapability("platformVersion"));
            final String browserName = String.valueOf(capabilities.getCapability("browserName"));
            final String browserVersion = String.valueOf(capabilities.getCapability("browserVersion"));

            String browserVersionFormatted;
            if ("null".equals(browserName)) {
                browserVersionFormatted = "default browser";
            } else {
                browserVersionFormatted = browserName + "-" + browserVersion;
            }


            String message = String.format("%n%s-%s %s : %d violations on %s%n",
                    platform, version, browserVersionFormatted, errorCount, DriverFactory.getDriver().getCurrentUrl());

            message = String.format("%s%n%s%n", message, errors);
            PerfectoHelper.getReportiumClient().stepEnd("AXE scan for: "+ pageName);

            String failScriptOnAccessibilityErrors = System.getenv("failScriptOnAccessibilityErrors");
            if (null != failScriptOnAccessibilityErrors && 0 ==  failScriptOnAccessibilityErrors.compareToIgnoreCase("true"))
                throw new AccessibilityException(message);

        }

    }

    private void perfectoComment(String comment){

        Map<String, Object> params = new HashMap<>();
        params.put("text", comment);
        try {
            DriverFactory.getDriver().executeScript("mobile:comment", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
