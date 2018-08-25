package webdriver.utils.db;

import lombok.SneakyThrows;
import webdriver.BaseEntity;
import webdriver.resources.PropertiesResourceManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static webdriver.resources.Constants.PROPERTIES_DB;

/**
 * The type Database conn my sql.
 */
public class DatabaseConnMySQL extends BaseEntity {
    private final PropertiesResourceManager props = new PropertiesResourceManager(PROPERTIES_DB);
    private Connection connection;

    /**
     * Gets connection.
     *
     * @return the connection
     */
    @SneakyThrows(Exception.class)
    private Connection getConnection() {
        if (connection == null) {
            Class.forName(props.getProperty(Property.MYSQL_JDBC_DRIVER_NAME.toString())).newInstance();
            LOGGER.info("Get connection from database");
            connection = DriverManager.getConnection(
                props.getProperty(Property.MYSQL_URL.toString()),
                props.getProperty(Property.MYSQL_USERNAME.toString()),
                props.getProperty(Property.MYSQL_PASSWORD.toString())
            );
        }
        return connection;
    }

    /**
     * Gets statement.
     *
     * @return the statement
     */
    public Statement getStatement() {
        try {
            return getConnection().createStatement();
        } catch (SQLException exception) {
            LOGGER.debug(String.format("Not statement from database. %s", exception));
        }
        return null;
    }

    /**
     * Close.
     */
    @SneakyThrows(SQLException.class)
    public void close() {
        LOGGER.info("DB connection is closing");
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
}