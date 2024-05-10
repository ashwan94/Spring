package kr.or.nextit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.*;

public class App {
    public static void main(String[] args) throws IOException {

        HSSFWorkbook workBook = new HSSFWorkbook();

        CellStyle defaultStyle = workBook.createCellStyle();

        // 테두리 설정
        defaultStyle.setBorderTop(BorderStyle.THIN);
        defaultStyle.setBorderLeft(BorderStyle.THIN);
        defaultStyle.setBorderRight(BorderStyle.THIN);
        defaultStyle.setBorderBottom(BorderStyle.THIN);

        // 줄 바꿈 및 중앙 정렬
        defaultStyle.setWrapText(true);
        defaultStyle.setAlignment(HorizontalAlignment.CENTER);
        defaultStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 시트 생성 및 셀 높이 설정
        HSSFSheet sheet = workBook.createSheet();
        sheet.setDefaultRowHeightInPoints(50);

        final int[][] pw = {{4, 2, 0}, {9, 1, 6}, {8, 7, 3}, {-1, 5, -2}};
        for (int i = 0; i < pw.length; i++) {
            Row row = sheet.createRow(i);

            for (int j = 0; j < pw[i].length; j++) {

                Cell cell = row.createCell(j);
                cell.setCellStyle(defaultStyle);

                int num =pw[i][j]; // cell 에 입력할 element

                if(num >= 0) {
                    cell.setCellValue((num));
                }else if(pw[i][j] == -1){
                    cell.setCellValue("BACK");
                }else{
                    cell.setCellValue("OK");
                }

                sheet.setColumnWidth(j, 2000);
            }
        }

        try {
            File xlsFile = new File("/Users/na/workspace/spring/makeExcel/src/main/resources/complicatedPW.xlsx");
            FileOutputStream fileOut = new FileOutputStream(xlsFile);
            workBook.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workBook.close();
        }
    }


}
