package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelUtil {

	static Object[][] data = null;
	
	public static Object[][] getData()
	{
		FileInputStream fin;
		XSSFWorkbook book;
		Sheet sheet;
		int rows = 0;
		int columns = 2;
		int count = 0;

		try {
			fin = new FileInputStream(new File(System.getProperty("user.dir") + "\\test-input\\users.xlsx"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
		try {
			book = new XSSFWorkbook(fin);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		sheet = book.getSheetAt(0);
		rows = sheet.getLastRowNum();
	
		data = new Object[rows][columns];

		for (int i=1; i<rows;i++)
		{
			Row row = sheet.getRow(i);

			Iterator <Cell> cell = row.cellIterator();
			count = 0;
		
			while(cell.hasNext() && (count < columns))
			{
				Cell c = cell.next();
				data[i][count] = c.getStringCellValue();
				count++;
			}
		}

		try {
			book.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static boolean setData(int row, int col, String result)
	{
		String outputFile = System.getProperty("user.dir") + "\\test-input\\users.xlsx";
		FileInputStream fin;
		XSSFWorkbook book;
		Sheet sheet;

		try {
			fin = new FileInputStream(new File(outputFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	
		try {
			book = new XSSFWorkbook(fin);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		sheet = book.getSheetAt(0);
		Row r = sheet.getRow(row);
		Cell c = r.createCell(col);
		c.setCellValue(result);
		
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(outputFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    try {
			book.write(fos);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    try {
			fos.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    try {
			fos.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		try {
			book.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
