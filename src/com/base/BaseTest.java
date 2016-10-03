package com.base;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.util.Configuration;
import com.util.ObjectRepo;
import com.util.PageDriver;

public abstract class BaseTest {

	public PageDriver pd;
	
	public static final Configuration config;
	
	static
	{
		config = new Configuration();
	}

	@BeforeSuite
	public void beforeSuite()
	{
		this.pd = new PageDriver(config);
		ObjectRepo.readLocators();
	}

	@AfterSuite
	public void afterSuite()
	{
		// pd.quit();
	}
}
