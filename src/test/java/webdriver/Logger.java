package webdriver;

public final class Logger {
	private static final org.apache.log4j.Logger LOG4J = org.apache.log4j.Logger.getLogger(Logger.class);
	private static Logger instance = null;

    private Logger() {
        // do not instantiate Logger class
    }

  	public static synchronized Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
		}
		return instance;
	}

	public void step(final int step, final String description) {
		logStepMsg(String.format("%1$s. %2$s", step, description));
	}

	public void debug(Object message) {
		LOG4J.debug(message);
	}

    public void error(Object message) {
        LOG4J.error(message);
    }

	public void info(Object message) {
		LOG4J.info(message);
	}

	public void fatal(final String message) {
		LOG4J.fatal(message);
	}

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

	void logTestEnd(final String data) {
		logTestEnd(data, "Passed", "");
	}

	void logTestEnd(final String data, final String reason) {
		logTestEnd(data, "Failed", reason);
	}

	void logTestName(final String testName) {
		String formattedName = String.format("===================== '%1$s' =====================", testName);
		StringBuilder delims = new StringBuilder();
		int nChars = formattedName.length();
		for (int i = 0; i < nChars; i++) {
			delims.append("-"); }info(delims.toString());
		info(formattedName);
		info(delims.toString());
	}
}