package org.moara.yido.file;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import static org.junit.Assert.*;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileTest {

    @Test
    public void getFileTest() {

        FileManagerImpl fileManager = new FileManagerImpl();

        fileManager.readFile("/data/newRevData.txt");


        assertEquals(fileManager.getFile().get(1), "흠...포스터보고 초딩영화줄....오버연기조차 가볍지 않구나");
    }

    @Test
    public void writeFileTest() throws InterruptedException {
        FileManagerImpl fileManager = new FileManagerImpl();
        List<String> data = new ArrayList<>();

        data.add("test1");
        data.add("test2");
        data.add("test3");

        fileManager.writeFile(fileManager.pathSeparator + "data" + fileManager.pathSeparator + "test.role", data);

    }

    @Test
    public void readExcelTest() {


        ExcelUtil excelUtil = new ExcelUtil();
        FileManager fileManager = new FileManagerImpl();
        String dirPath = "D:\\moara\\data\\allData\\excel\\";
        List<File> fileList = excelUtil.getExcelFileList(dirPath);

        for (File file : fileList) {
            String fileName = file.getName();
            List<String> newsData = new ArrayList<>();

            XSSFSheet sheet = getExcelSheet(file, excelUtil);

            for (int i = 1; i < excelUtil.getRowCount(sheet); i++) {
                String data = excelUtil.getCellValue(sheet.getRow(i), 9);
                if (data == null) {
                    continue;
                }
                newsData.add(data);


            }

            fileManager.writeFile("/newsData/" + fileName + ".txt", newsData);
        }
    }

    private XSSFSheet getExcelSheet(File file, ExcelUtil excelUtil) {
        XSSFWorkbook work = null;
        try {
            work = new XSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        excelUtil.setXSSFWorkbook(work);

        return work.getSheetAt(0);
    }

}
