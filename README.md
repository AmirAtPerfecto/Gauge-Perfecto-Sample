# Perfecto Gauge Example
this example demonstrates two specs running on Perfecto using remoteWebDriver.

# Getting started

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

## examples
You can execute the following samples:
- GoogleTest.spec- web test (mobile/desktop)
- example.spec- same
- iOSCalculator- native iOS calculator test

## default environment definitions (env/default/default.properties)

Here are the default settings for execution. Below are instructions to customize and use those.
Please refer to http://developers.perfectomobile.com/display/PD/Supported+Appium+Capabilities for more insight

TARGET_EXECUTION = desktop

PLATFORM = Windows
PLATFORM_VERSION = 10
BROWSER = Chrome
BROWSER_VERSION = latest
RESOLUTION = 1280x1024
LOCATION = US East

## for mobile
##TARGET_EXECUTION = mobile

##PLATFORM = iOS
##PLATFORM_VERSION= 10.2

##MANUFACTURER = Apple
##MODEL = iPhone-6

##NETWORK = AT&T-United States of America
##DESCRIPTION = Group A
##RESOLUTION = 750 x 1334

## change browser
The default browser/OS settings is defined in "env/default/defaul.properties".

If you want to override the browser selection, simply add a new folder in "env" and copy "env/chrome/browser.properties" and override just what you want (you don't need to rewrite all capabilities).

Then, in the configuration (IntelliJ->Run->Edit configurations), in "environment" set the name of the folder.

## change to a mobile device (for mobile web or native execution)
Either in default.properties or in a new environment definition that you can define, set
## for mobile
TARGET_EXECUTION = mobile

PLATFORM = iOS
// the following are optional settings
##PLATFORM_VERSION= 10.2

##MANUFACTURER = Apple
##MODEL = iPhone-6

##NETWORK = AT&T-United States of America
##DESCRIPTION = Group A
##RESOLUTION = 750 x 1334

# Added support for accessibility scan (based on AXE) for mobile and desktop web pages
This project demonstrates how AXE (provided by Deque see [here](https://axe-core.org/docs/)) can be used to automate accessibility testing for any browser, desktop or mobile, with Quantum framework

In the report you will find a new step called **"Then axe scans the web page"** , this is what causes the scan

## Reading the Perfecto report
Under the step "Then axe scans the web page", you will find a number of "Execute Script" and then the first comment.
This comment will say **"Accessibility scan found 5 violations in 11 objects"**

Then, there will be a loop detailing each object and a follow up of a screenshot highlighting each object:

This is following the convention defined in the [AXE spec](https://axe-core.org/docs/#results-object)

**Impact: critical**\
**Rule ID: aria-required-children**\
**Summary: Fix any of the following: Required ARIA children role not present: listbox textbox**\
**Selector:	#lst-ib**\
**HTML:		input class="gsfi" id="lst-ib" maxlength="2048" name="q" autocomplete="off" title="Search" type="text" value="quantum perfecto" aria-label="Search" aria-haspopup="false" role="combobox" aria-autocomplete="list" dir="ltr" spellcheck="false" style="border: none; padding: 0px; margin: 0px; height: auto; width: 100%; background: url(&quot;data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw%3D%3D&quot;) transparent; position: absolute; z-index: 6; left: 0px; outline: none;"**\
**Help: Certain ARIA roles must contain particular children** \
**HelpURL: https://dequeuniversity.com/rules/axe/2.3/aria-required-children?application=axeAPI** \
**Tags: [cat.aria, wcag2a, wcag131]**

## Customizations
In the environment capabilities you can define failScriptOnAccessibilityErrors to true such that once a violation is found, their script will assert.

Kudis to Chris Emerson for his help setting this up!


# Added Applitools testing support
Applitools offers complete page comparison in CI

Added new steps to scan a page or scan a page with scroll:

I check window "Google Tom Brady Search"

I check complete window "Google Tom Brady Search"

To activate, set APPLITOOS_KEY with your key in env/default/default.properties. If you don't have a key head to applitools.com to get one.

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
