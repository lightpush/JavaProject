package org.example.excel;

import java.io.FileInputStream;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
    public static void main(String[] args) throws Exception {

    }

    public static HashMap<String, String> getMenuMap(String filePath) {
        HashMap<String, String> menuMap = new HashMap<>();

        try {
            FileInputStream file = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getPhysicalNumberOfRows();
            int[] price = new int[rows];
            String[] food = new String[rows];

            for (int rowindex = 0; rowindex < rows; rowindex++) {
                XSSFRow row = sheet.getRow(rowindex);
                if (row != null) {
                    XSSFCell priceCell = row.getCell(0);
                    XSSFCell foodCell = row.getCell(1);

                    if (priceCell != null && foodCell != null) {
                        if (priceCell.getCellType() == CellType.NUMERIC) {
                            price[rowindex] = (int) priceCell.getNumericCellValue();
                        } else if (priceCell.getCellType() == CellType.STRING) {
                            // 숫자 값을 가진 셀로 변환 시도
                            try {
                                price[rowindex] = Integer.parseInt(priceCell.getStringCellValue());
                            } catch (NumberFormatException ex) {
                                // 숫자로 변환할 수 없는 경우 예외 처리
                                System.out.println("Invalid numeric value in cell: " + priceCell.getStringCellValue());
                                continue;  // 다음 반복으로 건너뜁니다.
                            }
                        }

                        food[rowindex] = foodCell.getStringCellValue();

                        String menuPrice = String.valueOf(price[rowindex]);
                        String menuName = food[rowindex];
                        menuMap.put(menuPrice, menuName);
                        System.out.println(menuPrice + " " + menuName);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return menuMap;
    }

}
