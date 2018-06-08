package webdriver;

import org.testng.annotations.Test;

public abstract class BaseTest extends BaseEntity {
    public abstract void runTest();

    @Test
    public void xTest() {
        Class<? extends BaseTest> currentClass = this.getClass();
        try {
            logger.logTestName(currentClass.getName());
            runTest();
            logger.logTestEnd(currentClass.getName());
        } catch (Exception | AssertionError e) {
            logger.debug(e.getMessage());
            throw e;
        } finally {
            makeScreen(currentClass);
        }
    }
}