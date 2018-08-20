package webdriver.utils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.testng.SkipException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * Gets table array.
     *
     * @return the table array
     */
    public List<String> getTableArray() {
        List<String> tabArray = new ArrayList<>();
        try {
            Workbook workbook = Workbook.getWorkbook(new File(this.filepath));
            Sheet sheet = workbook.getSheet(0);

            for (int i = 0, rowCount = sheet.getRows(); i < rowCount; ++i) {
                Cell[] rowData = sheet.getRow(i);
                for (int j = 0, columnCount = sheet.getColumns(); j < columnCount; ++j) {
                    tabArray.add(rowData[j].getContents());
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