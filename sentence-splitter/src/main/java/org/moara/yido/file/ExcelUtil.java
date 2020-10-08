/** 
 * <pre>
 *  파 일 명 : ExcelUtil.java
 *  설    명 : MSOffice Excel용 유틸
 *                    
 *  작 성 자 : yeonie(김용수)
 *  작 성 일 : 2014.01
 *  버    전 : 1.4
 *  수정이력 : 2015.07, 2016.12, 2017.05(poi버젼 업데이트), 2017.06
 *  기타사항 :
 * </pre>
 * @author Copyrights 2014, 2015, 2016, 2017 by ㈜모아라. All right reserved.
 */

package org.moara.yido.file;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExcelUtil {
	private FileManagerImpl fileManager = new FileManagerImpl();
	private FormulaEvaluator formulaEvaluator;

	public FormulaEvaluator getFormulaEvaluator() {
		return formulaEvaluator;
	}

	/**
	 * 수식 계산을 위해서 시트의 FormulaEvaluator 클래스를 적용한다.
	 * @param formulaEvaluator
	 */
	public void setFormulaEvaluator(FormulaEvaluator formulaEvaluator){ this.formulaEvaluator = formulaEvaluator; }
	
	/**
	 * 수식계산을 위한 시트를 설정한다.
	 * @param xSSFWorkbook
	 */
	public void setXSSFWorkbook(XSSFWorkbook xSSFWorkbook){
		formulaEvaluator = xSSFWorkbook.getCreationHelper().createFormulaEvaluator();
	}
	
	/**
	 * cell의 값을 스트링 형태로 반환한다
	 * @param sheet sheet
	 * @param rowNum 행번호
	 * @param cellNum 열번호
	 * @return cell의값을 스트링형태로 반환
	 */
	public String getCellValue( XSSFSheet sheet, int rowNum, int cellNum){
		return getCellValue(sheet, rowNum, cellNum, null);
	}
	
	/**
	 * cell의 값을 스트링 형태로 반환한다 
	 * @param row row
	 * @param cellNum 열번호
	 * @return cell의값을 스트링형태로 반환
	 */
	public String getCellValue(XSSFRow row, int cellNum){
		
		return getCellValue(row, cellNum, null );
	}
	
	/**
	 * cell 값을 스트링 형태로 반환한다
	 * @param row row
	 * @param cellNum 열번호
	 * @param dateFormat 테이터 포멧(ex:yyyy.MM.dd HH:mm:ss) 날짜형식이 아닐경우 null 전달
	 * @return cell의 값을 스트링형태로 반환
	 */
	public String getCellValue(XSSFRow row, int cellNum, String dateFormat){
		if(row == null){
			return null;
		}
		return getCellValue(row.getCell(cellNum), dateFormat);
	}
	
	/**
	 * cell의 값을 스트링 형태로 반환한다 
	 * @param sheet sheet
	 * @param rowNum 행번호
	 * @param cellNum 열번호
	 * @param dateFormat 테이터 포멧(ex:yyyy.MM.dd HH:mm:ss) 날짜형식이 아닐경우 null 전달
	 * @return cell의값을 스트링형태로 반환
	 */
	public String getCellValue( XSSFSheet sheet, int rowNum, int cellNum, String dateFormat){
		XSSFRow row = sheet.getRow(rowNum);
		if(row == null){
			return null;
		}
		return getCellValue(row.getCell(cellNum), dateFormat);
	}
	
	/**
	 * cell의 값을 스트링 형태로 반환한다
	 * @param cell
	 * @return cell의값을 스트링형태로 반환
	 */
	public String getCellValue(XSSFCell cell){
		return getCellValue(cell, null);
	}
	
	/**
	 * cell의 값을 스트링 형태로 반환한다
	 * @param cell cell
	 * @param dateFormat 테이터 포멧(ex:yyyy.MM.dd HH:mm:ss) 날짜형식이 아닐경우 null 전달
	 * @return cell의값을 스트링형태로 반환 
	 */
	public String getCellValue(XSSFCell cell, String dateFormat){
		if(cell == null){
			return null;
		}
		
		switch(cell.getCellType()){
		case NUMERIC:
			return cellNumber(cell, dateFormat);	 
		case STRING:
			return cell.getStringCellValue();	   
		case BOOLEAN:
			return cell.getBooleanCellValue() + "";
		case ERROR:
			return cell.getErrorCellString();	           
		case BLANK:
			return null;
		case _NONE:
			return null;	
		case FORMULA:
			try{
	        	switch(formulaEvaluator.evaluateFormulaCell(cell)){
	    		case NUMERIC:
	    			return cellNumber(cell, dateFormat);	 
	    		case STRING:
	    			return cell.getStringCellValue();	   
	    		case BOOLEAN:
	    			return cell.getBooleanCellValue() + "";
	    		case ERROR:
	    			return cell.getErrorCellString();	           
	    		 default:
		        	return null;
	        	}
			}catch(Exception e){
				switch(cell.getCachedFormulaResultType()){
	    		case NUMERIC:
	    			return cellNumber(cell, dateFormat);	 
	    		case STRING:
	    			return cell.getStringCellValue();	   
	    		case BOOLEAN:
	    			return cell.getBooleanCellValue() + "";
	    		case ERROR:
	    			return cell.getErrorCellString();	           
	    		 default:
		        	return null;
				}
			}
			default:
        	return null;
		}

	}
	
	private String cellNumber(XSSFCell cell, String dateFormat){
		if(DateUtil.isCellDateFormatted(cell) && dateFormat != null){
    		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
    		return formatter.format(cell.getDateCellValue());
    	}
		String cellValue = Double.toString(cell.getNumericCellValue());
		if(cellValue.endsWith(".0"))
			cellValue = cellValue.substring(0, cellValue.length()-2);
		
		return cellValue ;  	
	}
	
	
	/**
	 * 경로내에 있는 모들 엑셀파일을 파일형태로 불러온다.
	 * @param path 폴더경로 또는 파일경로
	 * @return 파일리스트
	 */
	public List<File> getExcelFileList(String path){
		return fileManager.getFileList(path, ".xlsx");
	}

	
	/**
	 * row 개수 얻기
	 * @return
	 */
	public int getRowCount(XSSFSheet sheet){
		XSSFRow row; 
		int rowCount = sheet.getLastRowNum();
		 //엑셀 라스트 로우넘 버그처리
		 while(true){
			 try{
				 row = sheet.getRow(rowCount);			 
				 if(row == null){
					 break;
				 }
				 rowCount ++;							 
			 }catch(Exception e){
				 break;
			 }
			 
			
		 }
		 return rowCount;
	}
	
	/**
	 * Column 개수얻기
	 * @return
	 */
	public int getColumnCount(XSSFRow row){
		 int columnCount = row.getLastCellNum(); 
		 //컬럼 마지막인덱스가져오기 poi자체에대한 버그처리
		 while(true){
			 try{
				 XSSFCell cell = row.getCell(columnCount);
				 
				 if(cell == null){
					 break;
				 }
				 columnCount ++;							 
			 }catch(Exception e){
				 break;
			 }
		 }
		 return columnCount;
	}
}