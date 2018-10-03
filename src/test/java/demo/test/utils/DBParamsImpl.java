package demo.test.utils;

import lombok.Cleanup;
import webdriver.resources.PropertiesResourceManager;
import webdriver.utils.db.DatabaseConnMySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static webdriver.resources.Constants.PROPERTIES_DB_QUERIES;

/**
 * The type Db parameters.
 */
public class DBParamsImpl extends InitParams {
    private final PropertiesResourceManager props = new PropertiesResourceManager(PROPERTIES_DB_QUERIES);

    /**
     * Fetch test data object [ ].
     *
     * @param dataBaseLocation the data baselocation
     * @return the object [ ]
     */
    @Override
    public Object[] fetchTestData(String dataBaseLocation) {
        DatabaseConnMySQL database = new DatabaseConnMySQL();
        Statement statement = database.getStatement();
        String getEmailAccounts = this.props.getProperty("sql_get_email_accounts");
        List<String> data = new ArrayList<>();
        try {
            @Cleanup ResultSet rs = statement.executeQuery(getEmailAccounts);
            while (rs.next()) {
                data.add(rs.getString("login"));
                data.add(rs.getString("password"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            database.close();
        }
        logger.info(String.format("Data received from %s", dataBaseLocation));
        return getTestDataMails(data);
    }
}