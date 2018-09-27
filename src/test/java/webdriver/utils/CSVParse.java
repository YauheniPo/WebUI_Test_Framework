package webdriver.utils;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Csv parameters.
 */
public class CSVParse extends BaseParser {

    /**
     * Instantiates a new Csv parse.
     *
     * @param filepath the filepath
     */
    public CSVParse(String filepath) {
        super(filepath);
    }

    /**
     * Fetch csv data list.
     *
     * @return the list
     */
    public List<String> fetchCSVData() {
        List<String> data = new ArrayList<>();
        try {
            List<String[]> allRows = new CSVReader(new FileReader(this.filepath), ';').readAll();
            if (!allRows.isEmpty()) {
                allRows.forEach(row -> data.addAll(Arrays.asList(row)));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info(String.format("Data received from %s", this.filepath));
        return data;
    }
}