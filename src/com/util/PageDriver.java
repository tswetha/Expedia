package com.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.htmlunit.HtmlUnitDriver; 


public class PageDriver { // Factory Design Pattern

	private static WebDriver driver = null;
	private Configuration pageDriverConfig;
	private DesiredCapabilities capabilities;
	
	public PageDriver (Configuration config) 
	{
		this.pageDriverConfig = config;
		startBrowser();
	}

	public void startBrowser()
	{
		String browser = pageDriverConfig.BROWSER;
		
		if (driver != null)
		{
			return;
		}
		
		switch (browser) 				
		{
		case "firefox":  // if 
			driver = startFirefox(); 
			//break;
		case "chrome": // else if
			driver = startChrome();
			break;
		case "ie": // else if
			driver = startIE();
			break;
		case "html": // else if 
			driver = startHtml();
			break;	
		default: // else 
			driver = startHtml();
			break;
		}

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		
		driver.get(pageDriverConfig.URL);
	}

	public WebDriver startFirefox()
	{		 
		// method 1 - use ProfilesIni and load existing profiles like 'Default' using getProfile() method
		// ProfilesIni profile = new ProfilesIni();
		// FirefoxProfile fp = profile.getProfile("Default"); 

		// method 2 - use setPreference() method and set individual attributes separately
		FirefoxProfile fp = new FirefoxProfile();
		fp.setPreference("browser.startup.homepage_override.mstone", "ignore");
		fp.setPreference("browser.startup.homepage", "http://news.google.com");
		fp.setPreference("startup.homepage_welcome_url", "http://news.yahoo.com");
		fp.setPreference("startup.homepage_welcome_url.additional", "http://google.com");

		// Create a new instance of the Firefox driver - pass firefoxProfile to FirefoxDriver constructor
		// driver = new FirefoxDriver(fp);

		// method 3 - use DesiredCapabilities and setCapability() method
		capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability(FirefoxDriver.PROFILE, fp);
		capabilities.setBrowserName("firefox");
		capabilities.setPlatform(Platform.WIN10);
		   
		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\swetha\\Desktop\\java\\externalpackages\\geckodriver-v0.10.0-win64\\geckodriver.exe");
		
		// Create a new instance of the Firefox driver - pass desired capabilities to FirefoxDriver constructor
		return new FirefoxDriver(capabilities);
	}

	public WebDriver startChrome()
	{
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=C:\\Users\\swetha\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
		options.addArguments("--start-maximized");
        options.addArguments("-disable-cache");

		capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"));
		capabilities.setBrowserName("chrome");
		capabilities.setPlatform(Platform.WIN10);
		
		System.setProperty("webdriver.chrome.driver", 
				"C:\\Users\\swetha\\Desktop\\java\\externalpackages\\Selenium\\chromedriver.exe");

		return new ChromeDriver(capabilities);
	}

	public WebDriver startIE()
	{
		return new InternetExplorerDriver();
	}

	public WebDriver startHtml()
	{
		return new HtmlUnitDriver();
	}

	public void getURL(String url)
	{
		driver.get(url);
	}
	
	public WebDriverWait getWebDriverWait(long timeout)
	{
		return new WebDriverWait(driver, timeout);	
	}
	
	public Actions getActions()
	{
		return new Actions(driver);	
	}
	
	public String getPageTitle()
	{
		return driver.getTitle();
	}
	
	public String getPageText(String loc)
	{
		return findPageElement(loc).getText();
	}
	
	public WebElement findPageElement(String loc)
	{
		WebElement element = null;
		
		try {
			element = driver.findElement(ObjectRepo.findBy(loc)); // search in object repository returns By instance
		}
		catch (NoSuchElementException e)
		{
			Configuration.log.error("no such element found in DOM");
			e.printStackTrace();
		}
		
		return element;
	}

	public List<WebElement> findPageElements(String loc)
	{
		List <WebElement> elements = null;
		
		try {
			elements = driver.findElements(ObjectRepo.findBy(loc)); // search in object repository
		} catch (NoSuchElementException e)
		{
			Configuration.log.error("no such element found in DOM");
			e.printStackTrace();
		}
		
		return elements;
	}

	public static void scrollToElement(WebElement element) {
	    JavascriptExecutor jse = (JavascriptExecutor) driver;
	    jse.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void clickUsingJavaScript(WebElement element)
	{
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				Configuration.log.info("Clicking on element with using java script click");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);	
			} else {
				Configuration.log.error("Unable to click on element");
			}
		} catch (StaleElementReferenceException e) {
			Configuration.log.error("Element is not attached to the page document "+ e.getStackTrace());
		} catch (NoSuchElementException e) {
			Configuration.log.error("Element was not found in DOM "+ e.getStackTrace());
		} catch (Exception e) {
			Configuration.log.error("Unable to click on element "+ e.getStackTrace());
		}
	}
	
	public Set<String> findWindowHandles()
	{
		return driver.getWindowHandles();
	}
	
	public FluentWait<WebDriver> getFluentWait()
	{
		FluentWait wait = new FluentWait(driver);
		
		wait.withTimeout(10, TimeUnit.SECONDS);
		wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait.ignoring(NoSuchElementException.class);
		
		return wait;
	}
	
	public static void switchToPopupWindow() {
		
	    Set<String> handles = driver.getWindowHandles();
	    String subWindowHandle = null;
	    
	    Iterator<String> itr = handles.iterator();

	    while (itr.hasNext()) {
	        subWindowHandle = itr.next();
	    }
	    
	    driver.switchTo().window(subWindowHandle);
	}
	
	public void switchToWindow() 
	{
		String parentWindowHandler = driver.getWindowHandle(); // Store your parent window
		String subWindowHandler = null;

		Set<String> windowId = driver.getWindowHandles(); // get all window handles
		Iterator<String> iterator = windowId.iterator();
		
		while (iterator.hasNext()){
			subWindowHandler = iterator.next();
		}
		
		driver.switchTo().window(subWindowHandler); // switch to popup window
		Configuration.log.info("now in child window with title: " + driver.getTitle());
		// perform operations on popup
		driver.switchTo().activeElement();
		driver.switchTo().window(parentWindowHandler);  // switch back to parent window
		Configuration.log.info("now in parent window with title: " + driver.getTitle());
	}
	
	public Alert handlePopup()
	{
        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
		return alert;
	}
	
	public String getPageCurrentUrl()
	{
		return driver.getCurrentUrl();
	}
	
	public void quitBrowser()
	{
		driver.quit();
	}
	
	public void takeScreenShot(String outputFile)
	{
		File optionsPage = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(optionsPage, new File(outputFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/* Rest all classes should access page driver
 page driver internally uses webdriver*/