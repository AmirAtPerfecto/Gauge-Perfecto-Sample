# Perfecto Gauge Example
this example demonstrates two specs running on Perfecto using remoteWebDriver.

# how to get started

To simply run the project using default values:
1. Open "env/default/defaul.properties" and set the values for

PERFECTO_CLOUD (example: demo.perfectomobile.com)

PERFECTO_USERNAME (example: dan@gogo.com)

PERFECTO_SECURITY_TOKEN (taken from here: http://developers.perfectomobile.com/display/PD/Security+Token)

You can do the same in IntelliJ Run> edit configurations

2. Right click "GoogleTest.spec" and select "run" or "debug"

You will get the capabilities in the console, the execution details and then a link to Perfecto report "Report URL:"

You can do the same for "example.spec" or even "specs" folder.
In the latter case all specs will execute serially.

# change browser
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

# please note:

1. It does not seem like Gauge supports parallel execution of environments easily. At least from IntelliJ you can only test on one browser in parallel. You can probably do concurrent multi browser executions from your CI tool.

2. Although it seems like Gauge supports parallel execution of specs, in reality due to the static driver being shared, the tests will get confused and fail. This is really because there is not mapping between specs, Java classes and drivers. a spec can use more than one java class.
