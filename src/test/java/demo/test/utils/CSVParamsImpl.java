package demo.test.utils;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVParamsImpl extends InitParams {

    @Override
    public InitParams fetchTestData(String dataBaselocation) {
        List<String> data = new ArrayList();
        try {
            List<String[]> allRows = new CSVReader(new FileReader(dataBaselocation), ';').readAll();
            if (!allRows.isEmpty()) {
                for (String[] row : allRows) {
                    data.addAll(Arrays.asList(row));
                }
            }
        } catch (IOException e){
            LOGGER.error(e.getMessage());
        }
        setParams(data);
        LOGGER.info(String.format("Вata received from %s", dataBaselocation));
        return this;
    }
}