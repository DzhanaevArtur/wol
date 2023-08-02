import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class Temperature {

    private final static int ITER = (int) Math.pow(10, 3), WIDTH = 20, HEIGHT = 15;
    private final static int T1 = 9000, T2 = -10000, T3 = 100, T4 = 500;
    private final static double DELTA = Math.pow(10, -3);

    public static void main(String[] args) {
        double[][][] arr = new double[ITER][HEIGHT][WIDTH];
        for (int i = 0; i < ITER; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                for (int k = 0; k < WIDTH; k++) {
                    if (j == 0 && k != 0 && k != WIDTH - 1) borderFilling(arr, i, j, k, T1);
                    else if (j == HEIGHT - 1 && k != 0 && k != WIDTH - 1) borderFilling(arr, i, j, k, T2);
                    else if (k == 0 && j != 0 && j != HEIGHT - 1) borderFilling(arr, i, j, k, T3);
                    else if (k == WIDTH - 1 && j != 0 && j != HEIGHT - 1) borderFilling(arr, i, j, k, T4);
                    if (fillingTrigger(i, j, k)) {
                        research(arr, i, j, k);
                    }
                }
            }
        }
    }

    private static void borderFilling(double[][][] arr, int i, int j, int k, int filler) {
        arr[i][j][k] = filler;
    }

    private static boolean fillingTrigger(int i, int j, int k) {
        return i > 1 && j > 0 && j < HEIGHT - 1 && k > 0 && k < WIDTH - 1;
    }

    private static double averageFour(double[][][] arr, int i, int j, int k) {
        return (arr[i - 1][j - 1][k] + arr[i - 1][j + 1][k] + arr[i - 1][j][k - 1] + arr[i - 1][j][k + 1]) / 4;
    }

    private static boolean exitTrigger(double[][][] arr, int i, int j, int k) {
        return arr[i][j][k] > 0 && arr[i - 1][j][k] > 0 && Math.abs(arr[i][j][k] - arr[i - 1][j][k]) < DELTA;
    }

    private static void research(double[][][] arr, int i, int j, int k) {
        arr[i][j][k] = averageFour(arr, i, j, k);
        if (exitTrigger(arr, i, j, k)) {
            excel(arr[i - 1]);
            System.exit(0);
        }
    }

    private static void excel(double[][] arr) {
        FileOutputStream fileOutputStream;
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("Temperature");
        int rowid = 0;
        for (int i = 0; i < arr.length; i++) {
            double[] linear = arr[i];
            int cellid = 0;
            XSSFRow row = xssfSheet.createRow(rowid++);
            for (int j = 0; j < linear.length; j++) {
                double value = linear[j];
                XSSFCell cell = row.createCell(cellid++);
                XSSFCellStyle style = xssfWorkbook.createCellStyle();
                int min = Math.min(Math.min(T1, T2), Math.min(T3, T4));
                int red = (int) (255 * (value + Math.abs(min)) / (Math.abs(Math.max(Math.max(T1, T2), Math.max(T3, T4))) + Math.abs(min)));
                style.setFillForegroundColor(new XSSFColor(new Color(red, 0, 255 - red), new DefaultIndexedColorMap()));
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                if (!((i == 0 && j == 0) || (i == arr.length - 1 && j == 0) || (i == 0 && j == linear.length - 1) || (i == arr.length - 1 && j == linear.length - 1))) {
                    cell.setCellStyle(style);
                    cell.setCellValue(value);
                }
            }
        }
        try {
            fileOutputStream = new FileOutputStream("Temperature.xlsx");
            xssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
