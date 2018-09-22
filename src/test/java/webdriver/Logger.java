package webdriver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;

/**
 * The type Logger.
 */
@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Logger {
    private static Logger instance = null;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    @Synchronized
    public static Logger getInstance() {
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
        log.debug(message);
    }

    /**
     * Error.
     *
     * @param message the message
     */
    public void error(Object message) {
        log.error(message);
    }

    /**
     * Info.
     *
     * @param message the message
     */
    public void info(Object message) {
        log.info(message);
    }

    /**
     * Fatal.
     *
     * @param message the message
     */
    public void fatal(final String message) {
        log.fatal(message);
    }

    /**
     * Debug.
     *
     * @param message   the message
     * @param throwable the throwable
     */
    public void debug(Object message, Throwable throwable) {
        log.debug(message, throwable);
    }

    /**
     * Log step msg.
     *
     * @param msg the msg
     */
    private void logStepMsg(final String msg) {
        info(String.format("--------==[ %1$s.]==--------", msg));
    }
}