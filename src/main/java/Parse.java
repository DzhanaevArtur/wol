import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

@Slf4j
public class Parse {

    @SneakyThrows
    public static void main(String[] args) {
        for (Row row : new XSSFWorkbook(new FileInputStream("C:\\Users\\predator\\Downloads\\1.xlsx")).getSheetAt(0)) {
            if (String.valueOf(row.getCell(5)).equals("Да")) {
                log.info(String.valueOf(row.getCell(1)));
            }
        }
    }
}
