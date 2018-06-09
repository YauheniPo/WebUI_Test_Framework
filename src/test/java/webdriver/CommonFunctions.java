package webdriver;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class CommonFunctions {
	private static final Logger logger = Logger.getInstance();

	private CommonFunctions() {
		// do not instantiate CommonFunctions class
	}

	public static String parseDate(String date, String pattern) {
		String result = null;
		try {
			result = new SimpleDateFormat(pattern).parse(date).toString();
		} catch (ParseException e) {
			logger.debug("CommonFunctions.parseDate", e);
		}
		return result;
	}

	public static void centerMouse() {
		try {
			Robot robot = new Robot();
			int x = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
			int y = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
			robot.mouseMove(x / 2, y / 2);
		} catch (AWTException e) {
			logger.debug("CommonFunctions.centerMouse", e);
		}
	}
}