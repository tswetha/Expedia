package com.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.base.BasePage;
import com.util.PageDriver;

public class FlightsPage extends BasePage{

	public FlightsPage(PageDriver pd)
	{
		super(pd);
	}
	
	public void clickFlightsLink()
	{
		WebElement flightsLink = pd.findPageElement("id_flights_link");
		flightsLink.click();
	}

	public void clearFlightOrigin()
	{
		pd.findPageElement("id_origin").clear();
	}
	
	public void pickFlightOrigin(String s)
	{
		pd.findPageElement("id_origin").sendKeys(s);
	
		// select required airport from drop down list
		Actions fromAction = pd.getActions();
		WebElement fromAirport = pd.findPageElement("id_fromAirport"); 

		fromAction.moveToElement(fromAirport).perform();
		fromAction.click();
		fromAction.perform();
	}

	public void clearFlightDestination()
	{
		pd.findPageElement("id_destination").clear();
	}

	public void pickFlightDestination(String s)
	{
		pd.findPageElement("id_destination").sendKeys(s);

		// select airport from drop down list
		Actions toAction = pd.getActions();
		WebElement toAirport = pd.findPageElement("id_toAirport"); 
		toAction.moveToElement(toAirport).perform();
		toAction.click().perform();
	}

	public void clearFlightDepartingDate()
	{
		pd.findPageElement("id_departingDateBox").clear();
	}
	
	public void pickFlightDepartingDate()
	{
		pd.findPageElement("id_departingDateBox").click();
		
		Actions a = pd.getActions();

		WebElement returningDate = pd.findPageElement("xpath_departingDate");
		a.moveToElement(returningDate).perform();
		a.click().perform();
	}

	public void clearFlightReturningDate()
	{
		pd.findPageElement("id_departingDateBox").clear();
	}
	
	public void pickFlightReturningDate()
	{
		pd.findPageElement("id_returningDateBox").click();
		
		Actions a = pd.getActions();

		WebElement returningDate = pd.findPageElement("xpath_returningDate");
		a.moveToElement(returningDate).perform();
		a.click().perform();
	}

	public void selectNumberOfAdults(int n)
	{
		Select numAdults = new Select(pd.findPageElement("id_numAdults"));
		numAdults.selectByValue(String.valueOf(n));
	}

	public void selectNumberOfKids(int n)
	{
		Select numKids = new Select(pd.findPageElement("id_numKids"));
		numKids.selectByVisibleText(String.valueOf(n));
		/* 
		FluentWait fluentWait = pd.getFluentWait();
		fluentWait.until(ExpectedConditions.presenceOfElementLocated(ObjectRepo.findBy("id_Kid2Age")));		
		fluentWait.until(ExpectedConditions.presenceOfElementLocated(ObjectRepo.findBy("id_Kid3Age")));		
		fluentWait.until(ExpectedConditions.presenceOfElementLocated(ObjectRepo.findBy("id_Kid4Age")));
		*/
	}

	public void selectKid1Age(int n)
	{
		String s;
		
		WebElement element = pd.getWebDriverWait(2)
				   .until(ExpectedConditions.elementToBeClickable(pd.findPageElement("id_kid1Age")));
				
		Select kid1Age = new Select(element);
		
		if (n == 1) 
		{
			s = "Under 1";
		} 
		else 
		{
			s = String.valueOf(n);
		}
		
		kid1Age.selectByVisibleText(s);
	}
	
	public void selectInLapOrSeat(String s)
	{
		WebElement radioButton;
				 		
		if (s.equalsIgnoreCase("lap"))
		{
			radioButton = pd.getWebDriverWait(2)
				.until(ExpectedConditions.elementToBeClickable(pd.findPageElement("name_ChildrenInLap_RadioButton")));
		}
		else 
		{
			radioButton = pd.getWebDriverWait(2)
					.until(ExpectedConditions.elementToBeClickable(pd.findPageElement("name_ChildrenInSeat_RadioButton")));
		}
		
		radioButton.click();
	}

	public void selectKid2Age(int n)
	{
		WebElement element = pd.getWebDriverWait(2)
				   .until(ExpectedConditions.elementToBeClickable(pd.findPageElement("id_kid2Age")));
				
		Select kid2Age = new Select(element);
		kid2Age.selectByIndex(n);
	}

	public void selectKid3Age(String age)
	{		
		WebElement element = pd.getWebDriverWait(2)
				   .until(ExpectedConditions.elementToBeClickable(pd.findPageElement("id_kid3Age")));
				
		Select kid1Age = new Select(element);
		
		kid1Age.selectByVisibleText(age);
	}

	public void selectKid4Age(int n)
	{
		WebElement element = pd.getWebDriverWait(2)
				   .until(ExpectedConditions.elementToBeClickable(pd.findPageElement("id_kid4Age")));
				
		Select kid4Age = new Select(element);
		kid4Age.selectByIndex(n);
	}

	public void clickAdvancedOptionsLink()
	{
		if (!pd.findPageElement("id_non_stop_flight").isDisplayed())
		{
			pd.findPageElement("id_advancedOptions_link").click();
		}
	}
	
	public void selectNonStopFlight()
	{	
		WebElement element = pd.getWebDriverWait(2)
				   .until(ExpectedConditions.elementToBeClickable(pd.findPageElement("id_non_stop_flight")));
				
		if (!element.isSelected()) 
		{
			element.click();
		}
	}
	
	public void selectRefundableFlight()
	{	
		WebElement element = pd.getWebDriverWait(2)
				   .until(ExpectedConditions.elementToBeClickable(pd.findPageElement("id_refundable_flight")));
		
		if (!element.isSelected()) 
		{
			element.click();
		}
	}

	public void selectPreferredAirline(String airline)
	{
		Select preferredAirline = new Select(pd.findPageElement("id_preferredAirline"));
		preferredAirline.selectByValue(airline); // Delta airlines
	}

	public void selectPreferredClass(String airline_class)
	{
		new Select(pd.findPageElement("id_preferredClass")).selectByValue(airline_class);
	}
	
	public void clickSearchButton()
	{
		pd.findPageElement("id_searchButton").click();
	}
	
	public int countSearchResults()
	{
		WebElement element = pd.findPageElement("id_searchResultList"); 
		List<WebElement> list = element.findElements(By.cssSelector(".flight-module.segment.offer-listing"));

		return list.size();
	}
}
