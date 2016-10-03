package com.base;

import com.util.PageDriver;

public abstract class BasePage {

	public PageDriver pd;

	public BasePage(PageDriver pd)
	{
		//driver = new FireFoxdriver(); //dependency injection
		this.pd = pd;
	}
}
