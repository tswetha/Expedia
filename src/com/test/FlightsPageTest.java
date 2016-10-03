package com.test;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.BaseTest;
import com.page.FlightsPage;
import com.util.Configuration;
import com.util.ExcelUtil;
import com.util.ObjectRepo;

public class FlightsPageTest extends BaseTest {

	FlightsPage fp;

	@BeforeClass
	public void beforeClass()
	{
		fp = new FlightsPage(pd);
	}

	@Test (priority=0, enabled=true)
	public void testClickFlightsLink()
	{
		Configuration.log.info("Now executing testClickFlightsLink");
		fp.clickFlightsLink();

		Assert.assertEquals(pd.getPageCurrentUrl(), "https://www.expedia.com/Flights");
		Assert.assertEquals(pd.getPageTitle(), "Cheap Flights: Find One-Way Airline Tickets & Airfare Deals | Expedia");
	}

	@Test (priority=1, enabled=true)
	public void testClearFlightOrigin()
	{
		Configuration.log.info("Now executing testClearFlightsOrigin");

		fp.clearFlightOrigin();		
		Assert.assertEquals(pd.getPageText("id_origin"), "");
	}

	@Test (priority=1, enabled=true)
	public void testClearFlightDestination()
	{
		Configuration.log.info("Now executing testClearFlightDestination");

		fp.clearFlightOrigin();		
		Assert.assertEquals(pd.getPageText("id_destination"), "");
	}


	@Test (priority=2, enabled=true)
	@Parameters({ "origin" })
	public void testPickFlightOrigin(String origin)
	{
		//fp.pickFlightOrigin(origin);
		Object[][] data = ExcelUtil.getData();
		fp.pickFlightOrigin(data[1][0].toString());		
	}

	@Test (priority=2,enabled=true)
	@Parameters({"destination"})
	public void testPickFlightDestination(String destination)
	{
		Configuration.log.info("Now executing testPickFlightDestination");

		fp.pickFlightDestination(destination);	
		//fp.pickFlightDestination(ExcelUtil.getData()[1][1].toString());
	}

	@Test (priority=2,enabled=true)
	public void testPickFlightDepartingDate()
	{
		Configuration.log.info("Now executing testPickFlightDepartingDate");

		fp.pickFlightDepartingDate();	
	}

	@Test (priority=2,enabled=true)
	public void testPickFlightReturningDate()
	{
		Configuration.log.info("Now executing testPickFlightReturningDate");

		fp.pickFlightReturningDate();	
	}

	@Test (priority=2,enabled=true)
	@Parameters("adults")
	public void testPickNumberofAdults(int adults)
	{
		Configuration.log.info("Now executing testPickNumberofAdults");

		fp.selectNumberOfAdults(adults);	
	}

	@Test (priority=2,enabled=true)
	@Parameters("kids")
	public void testPickNumberofKids(int kids)
	{
		Configuration.log.info("Now executing testPickNumberofKids");

		fp.selectNumberOfKids(kids);	
	}

	@Test (priority=3,enabled=true)
	@Parameters("kid1Age")
	public void testPickKid1Age(int kid1Age)
	{
		Configuration.log.info("Now executing testPickKid1Age");

		fp.selectKid1Age(kid1Age);	
	}

	@Test (priority=3,enabled=true)
	@Parameters("kid2Age")
	public void testPickKid2Age(int kid2Age)
	{
		Configuration.log.info("Now executing testPickKid2Age");

		fp.selectKid2Age(kid2Age);	
	}

	@Test (priority=3,enabled=true)
	@Parameters("kid3Age")
	public void testPickKid3Age(int kid3Age)
	{
		Configuration.log.info("Now executing testPickKid3Age");

		fp.selectKid3Age(String.valueOf(kid3Age));	
	}

	@Test (priority=3,enabled=true)
	@Parameters("kid4Age")
	public void testPickKid4Age(int kid4Age)
	{
		Configuration.log.info("Now executing testPickKid4Age");

		fp.selectKid4Age(kid4Age);	
	}

	@Test (priority=4,enabled=true)
	@Parameters("laporseat")
	public void pickLapOrSeat(String laporseat)
	{
		Configuration.log.info("Now executing pickLapOrSeat");

		fp.selectInLapOrSeat(laporseat);	
	}

	@Test (priority=4,enabled=true)
	public void testClickAdvancedOptionsLink()
	{
		Configuration.log.info("Now executing testClickAdvancedOptionsLink");

		fp.clickAdvancedOptionsLink();	
	}

	@Test (priority=4,enabled=true)
	public void testSelectNonStopFlight()
	{
		Configuration.log.info("Now executing testSelectNonStopFlight");

		fp.selectNonStopFlight();	
	}

	@Test (priority=4,enabled=true)
	public void testSelectRefundableFlight()
	{
		Configuration.log.info("Now executing testSelectRefundableFlight");

		fp.selectRefundableFlight();	
	}

	@Test (priority=4,enabled=true)
	@Parameters("airline")
	public void testSelectPreferredAirline(String airline)
	{
		Configuration.log.info("Now executing testSelectPreferredAirline");

		fp.selectPreferredAirline(airline);	
	}

	@Test (priority=4,enabled=true)
	@Parameters("airline_class")
	public void testSelectPreferredAirlineClass(String airline_class)
	{
		Configuration.log.info("Now executing testSelectPreferredAirlineClass");

		fp.selectPreferredClass(airline_class);	
	}

	@Parameters({"optionsFile"})
	@Test(priority=5,enabled=true)
	public void takeOptionsSnapshot(String optionsFile)
	{
		Configuration.log.info("Now executing takeOptionsSnapshot");

		pd.takeScreenShot(optionsFile);
	}

	@Test (priority=6,enabled=true)
	public void testClickSearchButton()
	{
		Configuration.log.info("Now executing testClickSearchButton");

		fp.clickSearchButton();	
	}

	@Parameters({"resultsFile"})
	@Test(priority=7,enabled=true)
	public void takeResultsSnapShot(String resultsFile)
	{
		Configuration.log.info("Now executing takeResultsSnapShot");

		pd.takeScreenShot(resultsFile);
	}

	@Test(priority=7,enabled=true)
	public void TestCountSearchResults()
	{
		Configuration.log.info("Now executing TestCountSearchResults");
		int count = fp.countSearchResults();
		Configuration.log.info("Results page contains " + count + " flight options");
	}

	@Test(priority=8,enabled=true)
	public void contactUs()
	{
		pd.findPageElement("id_support_button").click();
		pd.findPageElement("id_customer_support_option").click();

		pd.clickUsingJavaScript(pd.findPageElement("css_contact_us_button"));

		FluentWait wait = pd.getFluentWait();
		wait.until(ExpectedConditions.elementToBeClickable(ObjectRepo.findBy("css_contact_us_modal_button")));

		pd.findPageElement("css_contact_us_modal_button").click();
	}

	@Test(priority=8,enabled=false)
	public void clickBellPopup()
	{
		pd.findPageElement("css_bell_popup").click();

		// FluentWait wait = pd.getFluentWait();
		// wait.until(ExpectedConditions.elementToBeClickable(ObjectRepo.findBy("css_bell_popup")));

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pd.handlePopup().dismiss();
	}

	@AfterMethod
	public void updateTestResult(ITestResult result) {
		int row = 1;
		int col = 2;

		if (result.getStatus() == ITestResult.FAILURE) {
			ExcelUtil.setData(row, col, "fail");
		} else {
			ExcelUtil.setData(row, col, "pass");
		}
	}

	@AfterClass
	public void closeBrowser()
	{
		pd.quitBrowser();
	}
}

