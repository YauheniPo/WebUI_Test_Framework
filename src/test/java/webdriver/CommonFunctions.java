package webdriver;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommonFunctions {
	private static final Logger logger = Logger.getInstance();

	private CommonFunctions() {
		// do not instantiate CommonFunctions class
	}

	private static Pattern regexGetPattern(String regex, boolean matchCase) {
		int flags;
		if (matchCase) {
			flags = 0;
		} else {
			flags = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
		}
		return Pattern.compile(regex, flags);
	}

	public static String regexGetMatch(String text, String regex) {
		return regexGetMatch(text, regex, false);
	}

	public static String regexGetMatch(String text, String regex, boolean matchCase) {
		return regexGetMatchGroup(text, regex, 0, matchCase);
	}

	public static boolean regexIsMatch(String text, String pattern) {
		return regexIsMatch(text, pattern, false);
	}

	public static boolean regexIsMatch(String text, String regex, boolean matchCase) {
		Pattern p = regexGetPattern(regex, matchCase);
		Matcher m = p.matcher(text);
		return m.find();
	}

	public static String regexGetMatchGroup(String text, String regex, int groupIndex) {
		return regexGetMatchGroup(text, regex, groupIndex, false);
	}

	public static String regexGetMatchGroup(String text, String regex, int groupIndex, boolean matchCase) {
		Pattern p = regexGetPattern(regex, matchCase);
		Matcher m = p.matcher(text);
		if (m.find()) {
			return m.group(groupIndex);
		} else {
			return null;
		}
	}

	public static int regexGetNumberMatchGroup(String text, String regex) {
		return regexGetNumberMatchGroup(text, regex, false);
	}

	public static int regexGetNumberMatchGroup(String text, String regex, boolean matchCase) {
		int number = 0;
		Pattern p = regexGetPattern(regex, matchCase);
		Matcher m = p.matcher(text);
		while (m.find()) {
			m.group();
			number++;
		}
		return number;
	}

	public static Calendar dateString2Calendar(String s) throws ParseException {
		Calendar cal = Calendar.getInstance();
		Date d1 = new SimpleDateFormat("dd.mm.yyyy").parse(s);
		cal.setTime(d1);
		return cal;
	}

	public static Date parseDate(String date) {
		return parseDate(date, "dd.mm.yyyy");
	}

	public static Date parseDate(String date, String pattern) {
		Date result = null;
		try {
			result = new SimpleDateFormat(pattern).parse(date);
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