package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Pre-Condition : The sheetName in the excel sheet must be Test Class Name.
 */

public class DataTable {

	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	HashMap<String, Integer> map = new HashMap<String, Integer>();

	public DataTable(String fileName) {

		// Instantiate the InputStream with File object as argument which
		// represent the xlsx file.
		// file located in Project classpath.
		InputStream inputFile;
		try {
			inputFile = new FileInputStream(new File(
					System.getProperty("user.dir") + "/src/test/resources/data/DESKTOP_WEB/" + fileName + "TestData.xlsx"));

			// Buffering the whole stream into memory.
			workbook = new XSSFWorkbook(inputFile);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	/**
	 * Read Excel Sheet from multiple tab
	 **/
	public HashMap<String, String> readExcelFromMultiTab(String sheetName) throws FileNotFoundException, IOException {
		return readSheetFromExcel(sheetName);
	}

	private HashMap<String, String> readSheetFromExcel(String sheetName) throws FileNotFoundException, IOException {
		sheet = workbook.getSheet(sheetName);
		int index = 0;
		// List of hashMap<Header,Corresponding value> storing values of each
		// cell in a row
		ArrayList<HashMap<String, String>> dataTableList = new ArrayList<>();

		// List of headers in excel file
		ArrayList<String> sheetHeader = new ArrayList<>();
		Iterator<Row> rowIterator = sheet.rowIterator();
		row = (XSSFRow) rowIterator.next();

		// Iterates through the first row in sheet and stores in Arraylist
		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			cell = (XSSFCell) cellIterator.next();
			sheetHeader.add(cell.getStringCellValue());

		}
		// Iterates each row one by one, creates the hashMap in each iteration
		// Stores Header of excel sheet as a 'Key' and corresponding value as
		// 'Value' in HashMap
		// Adds each HashMap in Arraylist
		while (rowIterator.hasNext()) {
			HashMap<String, String> newMap = new HashMap<>();
			row = (XSSFRow) rowIterator.next();
			cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				cell = (XSSFCell) cellIterator.next();
				newMap.put(sheetHeader.get(index++), cell.getStringCellValue());
			}
			dataTableList.add(newMap);
			index = 0;
		}

		// returns the first index of HashMap list
		return dataTableList.get(0);
	}


	/**
	 * Read Excel Sheet only having one sheet
	 **/
	public void readExcelFromOneSheetHavingMultipleTestCase() throws IOException {
		sheet = workbook.getSheetAt(0);
		Iterator<Row> rowiter = sheet.rowIterator();
		row = (XSSFRow) rowiter.next();
		while (rowiter.hasNext()) {
			row = (XSSFRow) rowiter.next();
			if (row.getCell(0) != null) {
				map.put(row.getCell(0).getStringCellValue(), row.getRowNum());
			}
		}
	}


	/**
	 * Map the test case name and its corresponding row value
	 **/
	public void capturesRowOfTestCasesInSheet(String sheetName) throws IOException {
		sheet = workbook.getSheet(sheetName);
		Iterator<Row> rowiter = sheet.rowIterator();
		if(map.isEmpty()==false){
			map.clear();
		}
		while (rowiter.hasNext()) {
			row = (XSSFRow) rowiter.next();
			if (row.getCell(0) != null) {
				map.put(row.getCell(0).getStringCellValue(), row.getRowNum());
			}
		}
	}

	/**
	@return the row number of test case 
	 */
	private Integer getRow(String key) {
		return map.get(key);
	}


	/**
	 * @return list of HashMap
	 *  Maps the testdata and its header		
	 * @throws Exception 
	 */	
	public List<HashMap<String, String>> preProcessAllTestData(String key) throws Exception {
		HashMap<String, String> mapTestCaseAndTestData;
		List<HashMap<String, String>> listofHashMap = new ArrayList<>();
		ArrayList<String> headerList = new ArrayList<>();
		int currRow;
		if(getRow(key) == null){
			throw new Exception(String.format("TestCase [%s] is not present in  TestData sheet; Also, check if sheet name is same as the class name;", key));
		}else{
			currRow = getRow(key);
		}
		row = sheet.getRow(currRow++);
		int rowsize = row.getLastCellNum();
		for (int i = 1; i < rowsize; i++) {
			headerList.add(row.getCell(i).getStringCellValue());
		}

		while (true) {
			row = sheet.getRow(currRow++);
			if (row == null) {
				break;
			} else {
				mapTestCaseAndTestData = new HashMap<>();
				for (int i = 1; i < rowsize; i++) {
					
					/* All values are string, If the row value is null, set value as empty string*/
					if(row.getCell(i) == null) {
						mapTestCaseAndTestData.put(headerList.get(i - 1), "");
					} else {
						DataFormatter df = new DataFormatter();
						String value = df.formatCellValue(row.getCell(i));
						mapTestCaseAndTestData.put(headerList.get(i - 1), value);
					}
				}
			}
			
			listofHashMap.add(mapTestCaseAndTestData);
		}
		return listofHashMap;
	}

}
