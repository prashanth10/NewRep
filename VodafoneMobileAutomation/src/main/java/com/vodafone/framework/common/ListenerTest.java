package com.vodafone.framework.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.IResultListener;

import com.perfectomobile.httpclient.utils.FileUtils;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;
import com.selenium.mobile.base.MobileTestCase;
import com.vodafone.framework.dto.Capability;
import com.vodafone.framework.dto.handler.CapabilityHandler;

import io.appium.java_client.AppiumDriver;

public class ListenerTest extends MobileTestCase implements IResultListener {
	protected static ExtentTest testReporter;
	@Override
	public void onTestStart(ITestResult result) {
		SimpleDateFormat ft = new SimpleDateFormat("mmddyy");
		String dateFormat = ft.format(new Date());
		reporter = new ExtentReports("build/Report " + dateFormat + ".html", true, DisplayOrder.NEWEST_FIRST,
				NetworkMode.ONLINE, Locale.ENGLISH);
		testReporter = reporter.startTest(result.getMethod().getMethodName(), "Description");
		testReporter.log(LogStatus.INFO, "Starting test " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String screenShotPath;
		try {
			screenShotPath = takescreenshot(driver);
			testReporter.log(LogStatus.PASS, "Test case paassed");
			testReporter.log(LogStatus.PASS, "Snapshot below: " + testReporter.addScreenCapture(screenShotPath));
			Reporter.log(screenShotPath);
			reporter.endTest(testReporter);
			reporter.flush();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Throwable Exception = result.getThrowable();
		String screenShotPath;
		try {
			testReporter.log(LogStatus.FAIL, "Test case failed");
			testReporter.log(LogStatus.FAIL, Exception);
			screenShotPath = takescreenshot(driver);
			testReporter.log(LogStatus.FAIL, "Snapshot below: " + testReporter.addScreenCapture(screenShotPath));
			Reporter.log(screenShotPath);
			reporter.endTest(testReporter);
			reporter.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String screenShotPath;
		try {
			testReporter.log(LogStatus.SKIP, "Test case skiped");
			screenShotPath = takescreenshot(driver);
			testReporter.log(LogStatus.SKIP, "Snapshot below: " + testReporter.addScreenCapture(screenShotPath));
			Reporter.log(screenShotPath);
			reporter.endTest(testReporter);
			reporter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onConfigurationSuccess(ITestResult itr) {
	}

	@Override
	public void onConfigurationFailure(ITestResult itr) {
	}

	@Override
	public void onConfigurationSkip(ITestResult itr) {
	}

	@Override
	public void onFinish(ITestContext context) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String takescreenshot(AppiumDriver driver) throws IOException {
		String imageName = System.currentTimeMillis() + ".png";
		File srcfile = driver.getScreenshotAs(OutputType.FILE);
		String dest = System.getProperty("user.dir") + "\\screenshot\\" + imageName;
		File destination = new File(dest);
		FileUtils.copyFile(srcfile, destination);
		return dest;
	}

	public void screenShots(WebDriver driver) {
		File file = new File("");
		Calendar lCDateTime = Calendar.getInstance();
		String a = lCDateTime.getTimeInMillis() + ".png";
		try {
			System.out.println(file.getCanonicalPath());
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String f = file.getCanonicalPath() + File.separator + "target" + File.separator + "surefire-reports"
					+ File.separator + lCDateTime.getTimeInMillis() + ".png";
			File dest = new File(f);
			FileUtils.copyFile(scrFile, dest);
			StringBuilder href = new StringBuilder();
			href.append("<a href=");
			href.append("'.." + File.separator + "surefire-reports");
			href.append(File.separator + a + "' target=\"_blank\">ScreenShot_");
			testReporter.log(LogStatus.SKIP,
					"Snapshot below: " + testReporter.addScreenCapture(href.toString() + "</a>"));
			Reporter.log(href.toString() + "</a>");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void hideKeyBoard() {
		try {
			driver.swipe(475, 200, 475, 500, 400);
			driver.hideKeyboard();
		} catch (Exception e) {
		}
	}

}