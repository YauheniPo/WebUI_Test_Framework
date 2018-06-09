package demo.test.utils;

public class DBParamsImpl extends InitParams {

    @Override
    public InitParams fetchTestData(String dataBaselocation) {

        LOGGER.info(String.format("Вata received from %s", dataBaselocation));
        return this;
    }
}