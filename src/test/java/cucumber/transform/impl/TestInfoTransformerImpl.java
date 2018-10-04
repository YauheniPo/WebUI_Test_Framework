package cucumber.transform.impl;

import cucumber.TestRunInfo;
import cucumber.transform.ITransformer;

public class TestInfoTransformerImpl implements ITransformer {
    @Override
    public Object transformData(String token) {
        token = token.split(getToken())[1];
        return TestRunInfo.getInstance().getTestInfoProperties().getProperty(token);
    }

    @Override
    public String getToken() {
        return "@testinfo.";
    }
}
