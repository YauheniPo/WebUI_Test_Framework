package webdriver.utils.db;

import webdriver.BaseEntity;
import webdriver.PropertiesResourceManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static webdriver.ConstantsFrm.PROPERTIES_DB;

public class DatabaseConnMySQL extends BaseEntity {
    private static final PropertiesResourceManager props = new PropertiesResourceManager(PROPERTIES_DB);
    private Connection connection;

    private Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(props.getProperty(Property.MYSQL_JDBC_DRIVER_NAME.toString())).newInstance();
                info("Get connection from database");
                connection = DriverManager.getConnection(
                        props.getProperty(Property.MYSQL_URL.toString()),
                        props.getProperty(Property.MYSQL_USERNAME.toString()),
                        props.getProperty(Property.MYSQL_PASSWORD.toString())
                );
            } catch (Exception exception) {
                info(String.format("Connection from database is denied. %s", exception));
            }
        }
        return connection;
    }

    public Statement getStatement() {
        try {
            return getConnection().createStatement();
        } catch (SQLException exception) {
            info(String.format("Not statement from database. %s", exception));
        }
        return null;
    }

    public void close() {
        info("DB connection is closing");
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                info(String.format("Connection is not close. %s", exception));
            }
            connection = null;
        }
    }
}