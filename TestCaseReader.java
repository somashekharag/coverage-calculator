package itaf.tools.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestCaseReader {

    public static Map<String, TestCaseInfo> getTestCases(String excelFilePath) throws IOException {
        Map<String, TestCaseInfo> testCases = new HashMap<>();

        FileInputStream fis = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0); // Assuming the test cases are in the first sheet

        for (Row row : sheet) {
            // Assuming the first cell is the Test Case ID, second is automation status, and third is the Test Case Name
            String testCaseId = row.getCell(0).getStringCellValue();
            String isAutomatable = row.getCell(1).getStringCellValue();
            String testCaseName = row.getCell(2).getStringCellValue();

            boolean automatable = isAutomatable.equalsIgnoreCase("yes");
            testCases.put(testCaseId, new TestCaseInfo(automatable, testCaseName));
        }

        workbook.close();
        return testCases;
    }

    public static class TestCaseInfo {
        private final boolean automatable;
        private final String name;

        public TestCaseInfo(boolean automatable, String name) {
            this.automatable = automatable;
            this.name = name;
        }

        public boolean isAutomatable() {
            return automatable;
        }

        public String getName() {
            return name;
        }
    }
}
