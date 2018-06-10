package webdriver.utils;

import au.com.bytecode.opencsv.CSVReader;
import webdriver.BaseEntity;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Csv parameters.
 */
public class CSVParse extends BaseEntity {
    private String filepath;

    /**
     * Instantiates a new Csv params.
     *
     * @param filepath the filepath
     */
    public CSVParse(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Fetch csv data list.
     *
     * @return the list
     */
    public List<String> fetchCSVData() {
        List<String> data = new ArrayList();
        try {
            List<String[]> allRows = new CSVReader(new FileReader(filepath), ';').readAll();
            if (!allRows.isEmpty()) {
                for (String[] row : allRows) {
                    data.addAll(Arrays.asList(row));
                }
            }
        } catch (IOException e){
            error(e.getMessage());
        }
        info(String.format("Data received from %s", filepath));
        return data;
    }
}