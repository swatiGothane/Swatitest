package com.applitools.quickstarts;

import com.applitools.eyes.*;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

public class BasicDemo {

	public static boolean getCI() {
		String env = System.getenv("CI");
		return Boolean.parseBoolean(env);
	}

	@Test
        public void test() {


		// Use Chrome browser
		WebDriver driver = new ChromeDriver(new ChromeOptions().setHeadless(getCI()));
		// Initialize the Runner for your test.
		EyesRunner runner = new ClassicRunner();

		// Initialize the eyes SDK
		Eyes eyes = new Eyes(runner);

		setUp(eyes);
		
		try {

			TestDemoApp(driver, eyes);

		} finally {

			tearDown(driver, runner);

		}

	}


	private static void setUp(Eyes eyes) {
		// Initialize the eyes configuration.
		Configuration config = eyes.getConfiguration();
		
		// Add this configuration if your tested page includes fixed elements.
		//config.setStitchMode(StitchMode.CSS);

		// You can get your api key from the Applitools dashboard
		config.setApiKey(System.getenv("APPLITOOLS_API_KEY"));

		// set new batch
		config.setBatch(new BatchInfo("Demo Batch - Selenium for Java - Classic"));

		// set the configuration to eyes
		eyes.setConfiguration(config);
	}

	private static void TestDemoApp(WebDriver driver, Eyes eyes) {
		// Set AUT's name, test name and viewport size (width X height)
		// We have set it to 800 x 600 to accommodate various screens. Feel free to
		// change it.
		eyes.open(driver, "Demo App - Selenium for Java - Classic", "Smoke Test - Selenium for Java - Classic", new RectangleSize(800, 600));

		// Navigate the browser to the "ACME" demo app.
		driver.get("https://demo.applitools.com");

		// To see visual bugs after the first run, use the commented line below instead.
		// driver.get("https://demo.applitools.com/index_v2.html");

		// Visual checkpoint #1 - Check the login page. using the fluent API
		// https://applitools.com/docs/topics/sdk/the-eyes-sdk-check-fluent-api.html?Highlight=fluent%20api
		eyes.check(Target.window().fully().withName("Login Window"));

		// This will create a test with two test steps.
		driver.findElement(By.id("log-in")).click();

		// Visual checkpoint #2 - Check the app page.
		eyes.check(Target.window().fully().withName("App Window"));

		// End the test.
		eyes.closeAsync();

	}
	
	private static void tearDown(WebDriver driver, EyesRunner runner) {
		driver.quit();

		TestResultsSummary allTestResults = runner.getAllTestResults();
		System.out.println(allTestResults);
	}

}
