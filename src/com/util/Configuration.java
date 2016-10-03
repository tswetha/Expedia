package com.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Configuration {
	
	public String URL = "";
	public String BROWSER = "";
	public static Logger log = Logger.getLogger(Configuration.class.getName());

	public Configuration()
	{
		readConfiguration();
	}
	
	public void readConfiguration()
	{
		Properties prop = new Properties();
		FileReader fr;
		
		try {
			fr = new FileReader(new File("config.properties")); // path to config.properties file
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		try {
			prop.load(fr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BROWSER = prop.getProperty("browser");
		URL  = prop.getProperty("url");
	}

}
