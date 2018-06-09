package demo.test.utils;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVParamsImpl extends InitParams {

    @Override
    public InitParams fetchTestData(String dataBaselocation) {
        try {
            List<String[]> allRows = new CSVReader(new FileReader(dataBaselocation), ';').readAll();
            if (!allRows.isEmpty()) {
                senderMailLogin = allRows.get(0)[0];
                senderMailPassword = allRows.get(0)[1];
                recipientMailLogin = allRows.get(1)[0];
                recipientMailPassword = allRows.get(1)[1];
            }
        } catch (IOException e){
            LOGGER.error(e.getMessage());
        }
        LOGGER.info(String.format("Вata received from %s", dataBaselocation));
        return this;
    }
}