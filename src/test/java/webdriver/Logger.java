package webdriver;

/**
 * The type Logger.
 */
public final class Logger {
    private static final org.apache.log4j.Logger LOG4J = org.apache.log4j.Logger.getLogger(Logger.class);
    private static Logger instance = null;

    private Logger() {
        // do not instantiate Logger class
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Step.
     *
     * @param step        the step
     * @param description the description
     */
    public void step(final int step, final String description) {
        logStepMsg(String.format("%1$s. %2$s", step, description));
    }

    /**
     * Debug.
     *
     * @param message the message
     */
    public void debug(Object message) {
        LOG4J.debug(message);
    }

    /**
     * Error.
     *
     * @param message the message
     */
    public void error(Object message) {
        LOG4J.error(message);
    }

    /**
     * Info.
     *
     * @param message the message
     */
    public void info(Object message) {
        LOG4J.info(message);
    }

    /**
     * Fatal.
     *
     * @param message the message
     */
    public void fatal(final String message) {
        LOG4J.fatal(message);
    }

    /**
     * Debug.
     *
     * @param message   the message
     * @param throwable the throwable
     */
    public void debug(Object message, Throwable throwable) {
        LOG4J.debug(message, throwable);
    }

    private void logStepMsg(final String msg) {
        info(String.format("--------==[ %1$s.]==--------", msg));
    }

    private void logTestEnd(final String data, final String result, final String reason) {
        info("");
        String leftAlignFormat = "| %-20s | %-22s | %-32s |%n";
        info("+----------------------------------------------+------------------------+-------------------------------------+%n");
        info("| Account                                      | Result (Passed\\Failed) | Failures reason                     |%n");
        info("+----------------------------------------------+------------------------+-------------------------------------+%n");
        info(String.format(leftAlignFormat, data, result, reason));
        info("+----------------------------------------------+------------------------+-------------------------------------+%n");
    }

    /**
     * Log test end.
     *
     * @param data the data
     */
    void logTestEnd(final String data) {
        logTestEnd(data, "Passed", "");
    }

    /**
     * Log test end.
     *
     * @param data   the data
     * @param reason the reason
     */
    void logTestEnd(final String data, final String reason) {
        logTestEnd(data, "Failed", reason);
    }

    /**
     * Log test name.
     *
     * @param testName the test name
     */
    void logTestName(final String testName) {
        String formattedName = String.format("===================== '%1$s' =====================", testName);
        StringBuilder delims = new StringBuilder();
        int nChars = formattedName.length();
        for (int i = 0; i < nChars; i++) {
            delims.append("-");
        }
        info(delims.toString());
        info(formattedName);
        info(delims.toString());
    }
}