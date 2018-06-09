package webdriver;

import org.testng.annotations.Test;

public abstract class BaseTest extends BaseEntity {
    public abstract void runTest();

    /**
     *
     * @throws Throwable
     */
    @Test
    public void xTest() throws Throwable {
        Class<? extends BaseTest> currentClass = this.getClass();
        try {
            logger.logTestName(currentClass.getName());
            runTest();
            logger.logTestEnd(currentClass.getName());
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw ex;
        } catch (AssertionError as) {
            logger.logTestEnd(as.getMessage());
            makeScreen(currentClass);
            throw as;
        }
    }
}