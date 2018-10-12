package webdriver.utils.visual;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import webdriver.Logger;
import webdriver.driver.Browser;
import webdriver.utils.Helper;

/**
 * The type Sikuli conf.
 */
public class SikuliConf {
    private static final Logger LOGGER = Logger.getInstance();
    private Screen screen;

    /**
     * Instantiates a new Sikuli conf.
     */
    public SikuliConf() {
        LOGGER.info("Create screen");
        screen = new Screen();
    }

    /**
     * Exists boolean.
     *
     * @param screenPath the screen path
     *
     * @return the boolean
     */
    public boolean exists(String screenPath) {
        LOGGER.info("Check element is exists");
        return screen.exists(screenPath, Helper.fetchSeconds(Browser.TIMEOUT_FOR_CONDITION)) != null;
    }

    /**
     * Is deleted boolean.
     *
     * @param screenPath the screen path
     *
     * @return the boolean
     */
    public boolean isDeleted(String screenPath) {
        LOGGER.info("Check element is not exists");
        return screen.waitVanish(screenPath);
    }

    /**
     * Drag drop int.
     *
     * @param screenPath the screen path
     * @param workplace  the workplace
     *
     * @return the int
     *
     * @throws FindFailed the find failed
     */
    public int dragDrop(String screenPath, String workplace) throws FindFailed {
        LOGGER.info("Drag Drop element");
        return screen.dragDrop(screenPath, workplace);
    }

    /**
     * Wheel int.
     *
     * @param target    the target
     * @param direction the direction
     * @param steps     the steps
     *
     * @return the int
     *
     * @throws FindFailed the find failed
     */
    public int wheel(String target, int direction, int steps) throws FindFailed {
        LOGGER.info("Wheel element");
        return screen.wheel(target, direction, steps);
    }

    /**
     * Click int.
     *
     * @param target the target
     *
     * @return the int
     *
     * @throws FindFailed the find failed
     */
    public int click(String target) throws FindFailed {
        LOGGER.info("Click on the element");
        return screen.click(target);
    }
}
