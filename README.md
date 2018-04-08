# Perfecto Gauge Example
this example demonstrates two specs running on Perfecto using remoteWebDriver.

# how to get started

To simply run the project using default values:
1. Open "env/default/defaul.properties" and set the values for
PERFECTO_CLOUD (example: demo.perfectomobile.com) <br/>
PERFECTO_USERNAME (example: dan@gogo.com)
PERFECTO_SECURITY_TOKEN (taken from here: http://developers.perfectomobile.com/display/PD/Security+Token)

You can do the same in IntelliJ Run> edit configurations

2. Right click "GoogleTest.spec" and select "run" or "debug"

You will get the capabilities in the console, the execution details and then a link to Perfecto report "Report URL:"

You can do the same for "example.spec" or even "specs" folder.
In the latter case all specs will execute serially.

#change browser
The default browser/OS settings is defined in "env/default/defaul.properties".
If you want to override the browser selection, simply add a new folder in "env" and copy "env/chrome/browser.properties" and override just what you want (you don't need to rewrite all capabilities).
Then, in the configuration (IntelliJ->Run->Edit configurations), in "environment" set the name of the folder.

# about PerfectoHelper
Basically PerfectoHelper is a class of static RemoteWebDriver and its matching ReportiumClient.
Both are initialized on the execution of a spec and are terminated at the conclusion of a spec.
From there, Java code for specs can get the current driver or reportiumClient and drive actions or reports.

Note that while the driver and reportiumClient static variables are indeed static, they are assigned and disposed at the beginning/end of each spec

PerfectoHelper uses a static Exception variable to report if an error occured during the script execution. If one was reported, the test will be declared as failed and the exception message will be reported.

PerfectoHelper is being used from DriverFactory utilities.

# About this template

This is a template to get started with a Gauge project that uses Selenium as the driver to interact with a web browser.

## Installing this template

    gauge --install java_maven_selenium

## Building on top of this template

### Defining Specifications

 This template includes a sample specification which opens up a browser and navigates to `Get Started` page of Gauge.
 Add more specifications on top of sample specification.

Read more about [Specifications](http://getgauge.io/documentation/user/current/specifications/README.html)

### Writing the implementations

This is where the java implementation of the steps would be implemented. Since this is a Selenium based project, the java implementation would invoke Selenium APIs as required.

_We recommend considering modelling your tests using the [Page Object](https://github.com/SeleniumHQ/selenium/wiki/PageObjects) pattern, and the [Webdriver support](https://github.com/SeleniumHQ/selenium/wiki/PageFactory) for creating them._

- Note that every Gauge step implementation is annotated with a `Step` attribute that takes the Step text pattern as a parameter.
Read more about [Step implementations in Java](http://getgauge.io/documentation/user/current/test_code/java/java.html)

### Execution

 You can execute the specification as:

```
mvn test
```
