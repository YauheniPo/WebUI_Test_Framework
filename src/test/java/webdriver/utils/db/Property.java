package webdriver.utils.db;

public enum Property {
    MYSQL_PASSWORD("mysql_password"),
    MYSQL_USERNAME("mysql_username"),
    MYSQL_JDBC_DRIVER_NAME("mysql_jdbc_driver_name"),
    MYSQL_URL("mysql_url");

    private final String property;

    Property(String property) {
        this.property = property;
    }

    public String toString() {
        return property;
    }
}