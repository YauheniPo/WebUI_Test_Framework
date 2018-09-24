package webdriver.utils;

import webdriver.Logger;

class BaseParser {
    static final Logger LOGGER = Logger.getInstance();
    final String filepath;

    /**
     * Instantiates a new Base parser.
     *
     * @param filepath the filepath
     */
    BaseParser(String filepath) {
        this.filepath = filepath;
    }
}