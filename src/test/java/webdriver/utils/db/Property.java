package webdriver.utils.db;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum Property.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.MODULE)
public enum Property {
    MYSQL_PASSWORD("mysql_password"),
    MYSQL_USERNAME("mysql_username"),
    MYSQL_JDBC_DRIVER_NAME("mysql_jdbc_driver_name"),
    MYSQL_URL("mysql_url");

    private final String property;
}