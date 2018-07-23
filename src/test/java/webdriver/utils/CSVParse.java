package webdriver.utils;

import au.com.bytecode.opencsv.CSVReader;
import lombok.AllArgsConstructor;
import webdriver.BaseEntity;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Csv parameters.
 */
@AllArgsConstructor
public class CSVParse extends BaseEntity {
    private final String filepath;

    /**
     * Fetch csv data list.
     *
     * @return the list
     */
    public List<String> fetchCSVData() {
        List<String> data = new ArrayList<>();
        try {
            List<String[]> allRows = new CSVReader(new FileReader(filepath), ';').readAll();
            if (!allRows.isEmpty()) {
                for (String[] row : allRows) {
                    data.addAll(Arrays.asList(row));
                }
            }
        } catch (IOException e) {
            error(e.getMessage());
        }
        info(String.format("Data received from %s", filepath));
        return data;
    }
}