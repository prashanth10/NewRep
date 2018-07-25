package com.vodafone.mobile.pages;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.google.common.base.Function;
import com.relevantcodes.extentreports.LogStatus;
import com.vodafone.framework.common.ListenerTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

//this class is were we will use all the reuseable functions e.g getwindow handle.

public class Common extends ListenerTest {

	private static AppiumDriver driver;

	public Common(AppiumDriver driver) {
		super();
		this.driver = driver;
	}

	public static void waitUntil(By by) {
		WebDriverWait wait = new WebDriverWait(driver, 35);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
	}

	public static void log(String logs) {
		WebElement guru99seleniumlink;
		Reporter.log(logs);
		testReporter.log(LogStatus.INFO, logs);
		System.out.println(logs);

	}

	public static void waitforElment(String string) {
		for (int i = 0; i <= 10; i++) {
			impicitWait(1);
			try {
				if (driver.findElement(By.xpath(elementReader.getPropertyElement(string))).isDisplayed()) {
					break;
				} else {
					impicitWait(1);
				}
			} catch (Exception e) {
				impicitWait(2);
			}
		}
	}

	public static void log(String logs, boolean dis) {
		Reporter.log(logs, dis);
		testReporter.log(LogStatus.INFO, logs);

	}

	public static void log(String logs, int dis) {
		Reporter.log(logs, dis);
		testReporter.log(LogStatus.INFO, logs);
		System.out.println(logs);

	}

	public static void log(String logs, int integer, boolean dis) {
		Reporter.log(logs, integer, dis);
		testReporter.log(LogStatus.INFO, logs);
		System.out.println(logs);

	}

	public static void getWindowHandle(String title) {
		Set<String> handles = driver.getWindowHandles();
		if (handles.size() >= 1) {
			System.out.println("Number of broiwsers opened are" + handles.size());
			for (String handle : handles) {
				driver.switchTo().window(handle);
				if (driver.getTitle().contains(title)) {
					driver.getWindowHandle();
					break;
				}

			}
		}

	}

	public static void swipeUpElement(MobileElement element, int duration) {
		int topY = element.getLocation().getY();
		int bottomY = topY + element.getSize().getHeight();
		int centerX = element.getLocation().getX() + (element.getSize().getWidth() / 2);
		driver.swipe(centerX, bottomY, centerX, topY, duration);
	}

	public static void visibility(WebElement e) {
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', 'visibility: visible;');", e);
	}

	public static void display(WebElement e) {
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', 'display: block;');", e);
	}

	public static void top(WebElement e) {
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', 'top: -700px;');", e);
	}

	public static void top1(WebElement e, int px) {
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', 'top: -390px;');", e);
	}

	public static WebElement explicitWait(final By by) {
		WebElement element = null;
		try {
			// WebDriverWait wait = new WebDriverWait(driver, 30);
			// wait.until(ExpectedConditions.presenceOfElementLocated(by));
			final Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					// Wait for the condition
					.withTimeout(40, TimeUnit.SECONDS)
					// which to check for the condition with interval of 5
					// seconds.
					.pollingEvery(2, TimeUnit.SECONDS)
					// Which will ignore the NoSuchElementException
					.ignoring(NoSuchElementException.class).ignoring(TimeoutException.class)
					.ignoring(ElementNotVisibleException.class);
			element = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {

					return wait.until(ExpectedConditions.presenceOfElementLocated(by));
				}
			});

		} catch (TimeoutException toe) {
			Reporter.log("--with in specified time element not able to find out OR given locator is wrong--", true);
			toe.printStackTrace();
			Assert.assertTrue(false);
		}
		return element;
	}

	public static void slidermovement(WebElement e, int pixelsLeft, int pixelright) {
		Actions dragger = new Actions(driver);
		dragger.moveToElement(e).clickAndHold().moveByOffset(pixelsLeft, pixelright).release().perform();
	}

	public void dragAndDropBy() {
		Actions actn = new Actions(driver);
		actn.dragAndDropBy(driver.findElement(By.className("mCSB_dragger_bar")), 0, -50).build().perform();

	}

	public static void moveto(WebElement e) {
		try {
			Actions actn = new Actions(driver);
			actn.moveToElement(e).perform();
			impicitWait(10);
		} catch (MoveTargetOutOfBoundsException mtobe) {
			Reporter.log(
					"-please wait some more time and perform move to operation to avoid this exception-because when you are trying to move the element is not visible-",
					true);
			mtobe.printStackTrace();
		}

	}

	public static void swipeToElement(MobileElement e) {

		for (int i = 0; i < 4; i++) {
			try {
				e.isDisplayed();
				break;
			} catch (Exception q) {
				driver.swipe(475, 500, 475, 400, 400);
			}

		}
	}

	public static void swipeElementExample(WebElement el) {
		String orientation = ((AppiumDriver) driver).getOrientation().value();

		// get the X coordinate of the upper left corner of the element, then
		// add the element's width to get the rightmost X value of the element
		int leftX = el.getLocation().getX();
		int rightX = leftX + el.getSize().getWidth();

		// get the Y coordinate of the upper left corner of the element, then
		// subtract the height to get the lowest Y value of the element
		int upperY = el.getLocation().getY();
		int lowerY = upperY - el.getSize().getHeight();
		int middleY = (upperY - lowerY) / 2;

		if (orientation.equals("portrait")) {
			// Swipe from just inside the left-middle to just inside the
			// right-middle of the element over 500ms
			((AppiumDriver) driver).swipe(leftX + 5, middleY, rightX - 5, middleY, 500);
		} else if (orientation.equals("landscape")) {
			// Swipe from just inside the right-middle to just inside the
			// left-middle of the element over 500ms
			((AppiumDriver) driver).swipe(rightX - 5, middleY, leftX + 5, middleY, 500);
		}
	}

	/**
	 *
	 * @param locator
	 * @throws Exception
	 */
	// This was my breakthrough. My first partially working code.
	public void mouseOverClick(final WebElement e, WebDriver driver) {
		final long startTime = System.currentTimeMillis();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(2,
					TimeUnit.SECONDS);
			// .ignoring(NoSuchElementException.class );
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					try {
						Actions action = new Actions(driver);
						action.moveToElement(e).click().build().perform();
						implicitWait(2);
						return true;
					} catch (StaleElementReferenceException e) {
						return false;
					}
				}
			});
		} catch (NoSuchElementException nsee) {
			System.out.println("no such element exception::::");
		}

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Finished click after waiting for " + totalTime + " milliseconds.");
	}

	public void scrolling() {

		List<WebElement> image = driver.findElements(By.xpath(
				"/html/body/div[3]/div/div/div[2]/div[1]/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div[2]/div//span[2]"));

		for (WebElement clickimg : image) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", clickimg);
			Reporter.log(clickimg.getText(), true);
		}
	}

	public void contextclick(WebElement e) {
		Actions a = new Actions(driver);
		a.contextClick(e).perform();
	}

	public void swipeLeft() {
		Dimension size = driver.manage().window().getSize();
		int startx = (int) (size.width * 0.8);
		int endx = (int) (size.width * 0.20);
		int starty = size.height / 2;
		driver.swipe(startx, starty, endx, starty, 1000);
	}

	public static void dragAndDrop(AndroidDriver driver, WebElement drag, WebElement drop) {
		// Actions a = new Actions(driver);
		// a.dragAndDrop(drag, drop).release().perform();
		TouchAction action = new TouchAction((AndroidDriver) driver);
		action.longPress(drag).moveTo(drop).release().perform();
	}

	public static void impicitWait(int seconds) {
		try {
			seconds = seconds * 1000;
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void waitSleep(int time) {
		try {
			TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void clickHoldDragDrop(WebElement e, WebElement e1) {
		Actions action = new Actions(driver);
		action.moveToElement(e).build().perform();
		action.clickAndHold(e).build().perform();
		action.dragAndDrop(e, e1).build().perform();
		action.release(e).perform();

	}

	/**
	 * Page scroll down through java script.For down increase y-axis
	 */
	public static void scrollDown() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,400)", "");

	}

	public static void scrollUp() {
		// EventFiringWebDriver evd=new EventFiringWebDriver(driver);
		// evd.executeScript("window.scrollBy(100,600)", "");
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-200)", "");

	}

	public void scrollIntoView() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollTop = arguments[1];", driver.findElement(By.id("entity-detail-page")),
				100);

	}

	public void assertEquals(String s, String s1) {
		Assert.assertEquals(s, s1);
		Reporter.log("" + s1 + " and " + s + " are same", true);
	}

	public void verifyText(String s, String s1) {
		boolean flag = false;
		if (s1.contains(s)) {
			flag = true;
			Reporter.log("" + s1 + " and " + s + " are same", true);
			Assert.assertTrue(flag);
		} else {
			Reporter.log("" + s1 + " and " + s + " are not same", true);
			Assert.assertTrue(flag);
		}

	}

	public String mainWindowHandle() {
		String mainWindow = driver.getWindowHandle();
		System.out.println(mainWindow + ":main window");
		return mainWindow;
	}

	public String mainWindowTitle() {
		String mainWindowTitle = driver.getTitle();
		System.out.println(mainWindowTitle + ":main window title");
		return mainWindowTitle;
	}

	public void childWindowHandles(String mainWindowHandle) {
		// maximizeWindow();
		Set<String> s = driver.getWindowHandles();
		Iterator<String> ite = s.iterator();
		while (ite.hasNext()) {
			String childWindow = ite.next().toString();
			if (!childWindow.contains(mainWindowHandle)) {
				driver.switchTo().window(childWindow);
				String childWindow_title = driver.getTitle();
				System.out.println(childWindow_title + ":after switching child window");
				implicitWait(10);
			}
		}
	}

	public boolean switchToMainWidnow(String mainWindowTitle) {
		boolean flag = false;
		int dSize = driver.getWindowHandles().size();
		System.out.println("windows size before closing child window:" + dSize);
		if (dSize > 1) {
			driver.close();
		}
		int dSize1 = driver.getWindowHandles().size();
		System.out.println("windows size after closing child window:" + dSize1);
		Set<String> availableWindows = driver.getWindowHandles();
		if (!availableWindows.isEmpty()) {
			for (String windowId : availableWindows) {
				if (driver.switchTo().window(windowId).getTitle().equals(mainWindowTitle)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public void switchToDefault() {
		driver.switchTo().defaultContent(); // you are now outside both frames
	}

	public void waitForVisible(final By by, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	public static void implicitWait(int seconds) {
		try {
			driver.manage().timeouts().implicitlyWait(seconds * 5, TimeUnit.SECONDS);
		} catch (TimeoutException toe) {
			toe.printStackTrace();
		}
	}

	public void implicitWait(int seconds, WebDriver driver) {

		driver.manage().timeouts().implicitlyWait(seconds * 10, TimeUnit.SECONDS);
	}

	public void pageLoad(int seconds) {
		driver.manage().timeouts().pageLoadTimeout(seconds * 10, TimeUnit.SECONDS);
	}

	public boolean waitForElement(WebElement e, int sleeptTime, int rounds) {
		boolean flag = false;

		L1: for (int i = 0; i < rounds; i++) {
			Reporter.log("-waiting for element " + (i + 1) + " time-", true);
			impicitWait(sleeptTime);
			try {
				if (isElementPresent(e)) {
					flag = true;
					Assert.assertTrue(flag);
					break;
				} else {
					throw new Exception("element not displayed");
				}
			} catch (Exception ee) {
				Reporter.log("-wait for element-" + e, true);
				continue L1;
			}
		}

		if (flag) {
			Reporter.log("element displayed-" + flag, true);

		} else {
			Reporter.log("element not displayed in the given time-" + flag, true);

		}

		return flag;
	}

	public static boolean waitForDriver(WebElement e, int sleeptTime, int rounds) {
		boolean flag = false;
		L1: for (int i = 1; i <= rounds; i++) {

			try {
				Thread.sleep(1000 * sleeptTime);
				if (isElementPresent(e)) {
					flag = true;
					Assert.assertTrue(flag);
					break;

				} else {
					throw new Exception("element not displayed");
				}
			} catch (Exception ee) {
				Reporter.log(i + "-waiting for element-", true);
				continue L1;
			}
		}

		if (flag) {
			Reporter.log("-element displayed-" + flag, true);

		} else {
			Reporter.log("-element not displayed in the given time-" + flag, true);

		}

		return flag;
	}

	public boolean waitForDriver(WebElement e, int sleeptTime, int rounds, int subSleepTime) {
		boolean flag = false;
		L1: for (int i = 1; i <= rounds; i++) {
			driver.manage().timeouts().implicitlyWait(sleeptTime * 10, TimeUnit.SECONDS);
			try {
				if (isElementPresent(e)) {
					flag = true;
					Assert.assertTrue(flag);
					break;
				} else if (i == rounds) {
					driver.navigate().refresh();
					driver.manage().timeouts().implicitlyWait(subSleepTime * 10, TimeUnit.SECONDS);
					if (isElementPresent(e)) {
						flag = true;
						Assert.assertTrue(flag);
						Reporter.log("after reloading the page element is able to find out.", true);
						break;
					} else {
						throw new Exception("element not displayed");
					}
				} else {
					throw new Exception("element not displayed");
				}
			} catch (Exception ee) {
				Reporter.log(i + "-waiting for element-" + e, true);
				continue L1;
			}
		}

		if (flag) {
			Reporter.log("-element displayed-" + flag, true);

		} else {
			Reporter.log("-element not displayed in the given time-" + flag, true);

		}

		return flag;
	}

	public void waitForElement(final WebElement e, int timeOut, WebDriver driver) {
		new WebDriverWait(driver, timeOut) {
		}.until(new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driverObject) {
				return isElementPresent(e);

			}
		});
	}

	public static boolean isElementPresent(WebElement e) {
		boolean flag = false;
		try {
			if (e.isDisplayed()) {
				flag = true;
				Reporter.log("element is displayed-" + flag, true);
			}
		} catch (NoSuchElementException nsee) {
			flag = false;
			Reporter.log("element not displayed in the page or element locator has given wrongly-" + flag, true);
		}

		return flag;
	}

	public static boolean isElementPresent(AppiumDriver driver, By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * make the element to be clickable
	 **/
	public WebElement elementToBeClickable(final By by, int timeInSeconds) {
		return new FluentWait<WebDriver>(driver)
				// Wait for the condition
				.withTimeout(timeInSeconds * 10, TimeUnit.SECONDS)
				// which to check for the condition with interval of 5 seconds.
				.pollingEvery(2, TimeUnit.SECONDS)
				// Which will ignore the NoSuchElementException
				.ignoring(NoSuchElementException.class).ignoring(ElementNotVisibleException.class)
				.ignoring(WebDriverException.class).until(new ExpectedCondition<WebElement>() {
					public ExpectedCondition<WebElement> FluentWaitMethodNameObj = ExpectedConditions
							.elementToBeClickable(by);

					public WebElement apply(WebDriver driver) {
						WebElement element = FluentWaitMethodNameObj.apply(driver);
						try {
							if (element != null && element.isDisplayed()) {
								return element;
							} else {
								return null;
							}
						} catch (StaleElementReferenceException e) {
							Reporter.log("--Stale Element Exception--", true);
							e.printStackTrace();
							return null;
						} catch (TimeoutException toe) {
							Reporter.log("--Time Out Exception--", true);
							toe.printStackTrace();
							return null;
						}
					}

				});
	}

	public WebElement presenceOfElement(final By by, int timeInSeconds) {

		return new FluentWait<WebDriver>(driver)
				// Wait for the condition
				.withTimeout(timeInSeconds * 10, TimeUnit.SECONDS)
				// which to check for the condition with interval of 5 seconds.
				.pollingEvery(2, TimeUnit.SECONDS)
				// Which will ignore the NoSuchElementException
				.ignoring(NoSuchElementException.class).ignoring(ElementNotVisibleException.class)
				.ignoring(WebDriverException.class).until(new ExpectedCondition<WebElement>() {
					public ExpectedCondition<WebElement> FluentWaitMethodNameObj = ExpectedConditions
							.presenceOfElementLocated(by);

					public WebElement apply(WebDriver driver) {
						WebElement element = FluentWaitMethodNameObj.apply(driver);
						try {
							if (element != null && element.isDisplayed()) {
								return element;
							} else {
								return null;
							}
						} catch (StaleElementReferenceException e) {
							Reporter.log("--Stale Element Exception--", true);
							e.printStackTrace();
							return null;
						} catch (TimeoutException toe) {
							Reporter.log("--Time Out Exception--", true);
							toe.printStackTrace();
							return null;
						}
					}

				});
	}

	/**
	 * 
	 * @param locator
	 * @return
	 */
	public WebElement visibilityOfElement(final By by, int timeInSeconds) {
		return new FluentWait<WebDriver>(driver)
				// Wait for the condition
				.withTimeout(timeInSeconds * 10, TimeUnit.SECONDS)
				// which to check for the condition with interval of 5 seconds.
				.pollingEvery(2, TimeUnit.SECONDS)
				// Which will ignore the NoSuchElementException
				.ignoring(NoSuchElementException.class).ignoring(ElementNotVisibleException.class)
				.ignoring(WebDriverException.class).until(new ExpectedCondition<WebElement>() {
					public ExpectedCondition<WebElement> FluentWaitMethodNameObj = ExpectedConditions
							.visibilityOfElementLocated(by);

					public WebElement apply(WebDriver driver) {
						WebElement element = FluentWaitMethodNameObj.apply(driver);
						try {
							if (element != null && element.isDisplayed()) {
								return element;
							} else {
								return null;
							}
						} catch (StaleElementReferenceException e) {
							Reporter.log("--Stale Element Exception--", true);
							e.printStackTrace();
							return null;
						} catch (TimeoutException toe) {
							Reporter.log("--Time Out Exception--", true);
							toe.printStackTrace();
							return null;
						}
					}

				});
	}


	public static void adbCommand(String shellCommandcommand, long time) {

		String command = String.format(shellCommandcommand);
		Process process = null;

		try {
			process = Runtime.getRuntime().exec(command);
			System.out.println("after adb command");
			Thread.sleep(time);

		} catch (Exception e2) {
			// TODO Auto-generated catch block

		}
	}

	public static void waitmethod() {
		try {
			By e = By.id("xyz");
			driver.findElement(e).click();
		} catch (Exception p) {
			// TODO Auto-generated catch block

		}
	}

	public static void swipeUP() {
		driver.swipe(475, 500, 475, 200, 400);
		Common.impicitWait(5);
	}

	public static void swipeUP1() {
		driver.swipe(700, 450, 700, 250, 400);
		Common.impicitWait(5);
	}

	public static void swipeDown() {
		driver.swipe(475, 300, 475, 700, 400);// (475, 200, 475, 500, 400)
		Common.impicitWait(5);
	}

	public static void resetApp() {
		impicitWait(5);
		((AndroidDriver) driver).resetApp();
		impicitWait(15);
	}

	public static void reLaunchApp() {
		driver.closeApp();
		driver.launchApp();
		Common.impicitWait(3);
	}

	public static void scrollTo(By element, int count) {
		Boolean isExists = false;
		for (int i = 0; i < count; i++) {
			isExists = Common.isElementPresent(driver, element);
			Reporter.log("" + isExists, true);
			if (isExists) {
				break;
			} else {
				Common.swipeUP();
			}
		}
		Reporter.log("" + isExists, true);
	}

	public static void scrollToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("element", ((RemoteWebElement) element).getId());
		js.executeScript("mobile: scroll", scrollObject);
		Common.impicitWait(6);
	}

	public static void scrollToDown() {
		Dimension dimensions = driver.manage().window().getSize();
		Double screenHeightStart = dimensions.getHeight() * 0.5;
		int scrollStart = screenHeightStart.intValue();
		System.out.println("s=" + scrollStart);
		Double screenHeightEnd = dimensions.getHeight() * 0.2;
		int scrollEnd = screenHeightEnd.intValue();
		driver.swipe(0, scrollStart, 0, scrollEnd, 2000);
	}

	public static void scrollToUp() {
		Dimension dimensions = driver.manage().window().getSize();
		Double screenHeightStart = dimensions.getHeight() * 0.2;
		int scrollStart = screenHeightStart.intValue();
		System.out.println("s=" + scrollStart);
		Double screenHeightEnd = dimensions.getHeight() * 0.5;
		int scrollEnd = screenHeightEnd.intValue();
		driver.swipe(0, scrollStart, 0, scrollEnd, 2000);
	}

	public static void scrollUntilTextExists(String expected) {
		Map<String, Object> pars = new HashMap<>();
		// driver.executeScript("mobile:objects.optimization:start", pars);
		for (int i = 0; i <= 10; i++) {
			if (!driver.getPageSource().contains(expected)) {
				scrollToDown();
			}
		}
	}

	public static boolean scrollToElementaxct(By by) {
		boolean done = false;
		for (int i = 0; i <= 8; i++) {
			try {
				// future.get(8, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				if (driver.findElement(by).isDisplayed()) {
					done = true;
					break;
				}
			} catch (Exception e) {
			}
			scrollToDown();
		}
		return done;
	}

	public static void scrollChannelsUp(int j, WebElement by) {
		System.out.println("Scroll Up");

		for (int i = 0; i < j; i++) {
			Dimension size = driver.manage().window().getSize();

			int starty = (int) (size.height * 0.80);

			// Find endy point which is at top side of screen.
			int endy = (int) (size.height * 0.25);

			// Find horizontal point where you wants to swipe. It is in middle
			// of screen width.
			int startx = size.width / 2;

			// Swipe from Bottom to Top.
			driver.swipe(0, starty, 0, endy, 2000);
			if (by != null) {
				break;
			}
		}

	}

	public static void scrollTillElementFound(By by) {
		WebElement element = driver.findElement(by);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean scrollFound(WebElement webelement, int scrollPoints) {
		try {
			Actions dragger = new Actions(driver);
			// drag downwards
			int numberOfPixelsToDragTheScrollbarDown = 10;
			for (int i = 10; i < scrollPoints; i = i + numberOfPixelsToDragTheScrollbarDown) {
				dragger.moveToElement(webelement).clickAndHold().moveByOffset(0, numberOfPixelsToDragTheScrollbarDown)
						.release(webelement).build().perform();
			}
			Thread.sleep(500);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void scrollDirection(String Id, SwipeElementDirection arg) {
		MobileElement e = (MobileElement) driver.findElement(By.id(Id));
		e.swipe(arg, 1000);
	}

	public static WebDriver setContextToWebView() {
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
			System.out.println("contextName::" + contextName);
		}
		return driver.context((String) contextNames.toArray()[1]);
	}

	public static WebDriver setContextToNativeApp() {
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
			System.out.println("contextName::" + contextName);
		}
		return driver.context((String) contextNames.toArray()[0]);
	}

	public static void swipelessUP() {
		driver.swipe(475, 500, 475, 350, 400);
		Common.impicitWait(5);
	}

	public static void swipelimitlessUP() {
		driver.swipe(475, 500, 475, 275, 400);
		Common.impicitWait(5);
	}

	public static void hideKeyboard() {
		try {
			driver.hideKeyboard();
		} catch (Exception e) {

		}
	}

	public static void longPress(AppiumDriver driver, WebElement drag) {
		TouchAction action = new TouchAction((AndroidDriver) driver);
		action.longPress(drag).release().perform();
	}

	// The first web element should be maximum coordinate value than second one
	public boolean verifyPositionOfelement(WebElement we1, WebElement we2) {
		Boolean flag = true;
		Point pos1 = we1.getLocation();
		// int YCord1 = pos1.getY();
		int XCord1 = pos1.getX();

		Point pos2 = we2.getLocation();
		// int YCord2 = pos2.getY();
		int XCord2 = pos2.getX();
		int diffOfPosn = XCord2 - XCord1;
		while (diffOfPosn < 0) {
			System.out.println(flag);
			flag = false;
			break;
		}
		return flag;

	}

	// The first web element should be maximum coordinate value than second one
	public boolean verifyPsitionVertically(WebElement we1, WebElement we2) {
		Boolean flag = true;
		Point pos1 = we1.getLocation();
		int YCord1 = pos1.getY();
		// int XCord1 = pos1.getX();

		Point pos2 = we2.getLocation();
		int YCord2 = pos2.getY();
		// int XCord2 = pos2.getX();
		// int diffOfPosn = XCord2 - XCord1 ;
		int diffOfPosn = YCord2 - YCord1;
		while (diffOfPosn < 0) {
			System.out.println(flag);
			flag = false;
			break;
		}
		return flag;

	}

	public static void clearapplicationdata() throws InterruptedException {
		// TODO Auto-generated method stub
		driver.resetApp();
		Thread.sleep(3000);
		driver.launchApp();
		Thread.sleep(3000);
	}
}
