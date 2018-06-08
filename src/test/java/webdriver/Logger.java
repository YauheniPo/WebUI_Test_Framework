package webdriver;

public final class Logger {
	private static final org.apache.log4j.Logger LOG4J = org.apache.log4j.Logger.getLogger(Logger.class);
	private static Logger instance = null;

    private Logger() {
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

	private void logStepMsg(final String msg) {
		info(String.format("--------==[ %1$s.]==--------", msg));
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

	void logTestEnd(final String testName) {
    	info("");
    	String formattedEnd = String.format("***** %1$s: *****", testName);
    	StringBuilder stars = new StringBuilder();
    	int nChars = formattedEnd.length();
    	for (int i = 0; i < nChars; i++) {
    		stars.append("*"); }info(stars.toString());
    	info(formattedEnd);
    	info(stars.toString());
    	info("");
	}

	public void debug(Object message) {
		LOG4J.debug(message);
	}

    public void debug(Object message, Throwable throwable) {
        LOG4J.debug(message, throwable);
    }

	public void info(Object message) {
		LOG4J.info(message);
	}

	void error(final String message) {
		LOG4J.error(message);
	}

	void fatal(final String message) {
		LOG4J.fatal(message);
	}
}