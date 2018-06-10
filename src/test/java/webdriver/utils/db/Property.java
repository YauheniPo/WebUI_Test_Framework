package webdriver.utils.db;

/**
 * The enum Property.
 */
public enum Property {
    /**
     * Mysql password property.
     */
    MYSQL_PASSWORD("mysql_password"),
    /**
     * Mysql username property.
     */
    MYSQL_USERNAME("mysql_username"),
    /**
     * Mysql jdbc driver name property.
     */
    MYSQL_JDBC_DRIVER_NAME("mysql_jdbc_driver_name"),
    /**
     * Mysql url property.
     */
    MYSQL_URL("mysql_url");

    private final String property;

    Property(String property) {
        this.property = property;
    }

    public String toString() {
        return property;
    }
}