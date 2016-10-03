package com.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;

public class ObjectRepo {

	static HashMap<String,String> LocatorsHashMap;

	public static void readLocators()
	{
		Properties prop = new Properties();
		FileReader fr;
		LocatorsHashMap = new HashMap<>();

		try {
			fr = new FileReader(new File("ObjectRepo.properties"));
			prop.load(fr);
			Set <Entry<Object, Object>> entries = prop.entrySet();
			for(Entry<Object, Object> val : entries)
			{
				String elementName = (String) val.getKey(); // returns tester_name
				String value = (String) val.getValue(); // returns developer_name
				LocatorsHashMap.put(elementName,value); // insert {tester_name(key), developer_name(value)} into hashmap
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public static By findBy(String elementName) // input is tester element name
	{
		String s = LocatorsHashMap.get(elementName); // search for developer name in hashmap i.e object repository. 
													// finally s contains developer element name

		if (elementName.contains("_")) // parse tester Name and look for _
		{
			String[] val = elementName.split("_"); // split into array of strings
						
			if (val[0].equals("css")) // is it css ?
			{
				return By.cssSelector(s); // search by developer_name in DOM
			}
			else if(val[0].equals("xpath"))
			{
				return By.xpath(s);
			}
			else if(val[0].equals("id"))
			{
				return By.id(s);
			}
			else if(val[0].equals("name"))
			{
				return By.name(s);
			}
			else if(val[0].equals("link"))
			{
				return By.linkText(s);
			}
			else if(val[0].equals("plink"))
			{
				return By.partialLinkText(s);
			}
			else if(val[0].equals("class"))
			{
				return By.className(s);
			}
			else if(val[0].equals("tag"))
			{
				return By.tagName(s);
			}
		}
		
		Configuration.log.error("invalid element found in object repo: no _ character " + elementName);
		return null;
	}
}
