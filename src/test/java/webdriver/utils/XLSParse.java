package webdriver.utils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.testng.SkipException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The type XLS parse.
 */
public class XLSParse extends BaseParser {

    /**
     * Instantiates a new XLS parse.
     *
     * @param filepath the filepath
     */
    public XLSParse(String filepath) {
        super(filepath);
    }

    /**
     * Get table array string [ ] [ ].
     *
     * @return the string [ ] [ ]
     */
    protected Object[][] getTableArray() {
        String[][] tabArray = null;
        try {
            Workbook workbook = Workbook.getWorkbook(new File(this.filepath));
            Sheet sheet = workbook.getSheet(0);
            int rowCount = sheet.getRows();

            int columnCount = sheet.getColumns();

            tabArray = new String[rowCount - 1][columnCount];
            for (int i = 1; i < rowCount; i++) {
                Cell[] rowData = sheet.getRow(i);
                for (int j = 0; j < columnCount; j++) {
                    tabArray[i - 1][j] = rowData[j].getContents();
                }
            }
            workbook.close();
        } catch (FileNotFoundException e) {
            throw new SkipException("File not found: " + this.filepath);
        } catch (BiffException | IOException e) {
        }
        return tabArray;
    }
}