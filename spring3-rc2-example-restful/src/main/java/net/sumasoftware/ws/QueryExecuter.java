package net.sumasoftware.ws;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * @author Renan Huanca
 * @since Nov 30, 2009 8:04:45 PM
 */
public class QueryExecuter {
    private static QueryExecuter queryExecuter;

    public QueryExecuter getIntance(){

        if(queryExecuter == null){
            queryExecuter = new QueryExecuter();
        }
        return queryExecuter;
    }

    public ResultSet executeQuery(String connectionName, String sql){
        DBConnection dbConnection = ConnectionManager.getInstance().getConnectionProperties(connectionName);
        DataSource dataSource = DataSourceFactory.getDataSource(dbConnection);
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            
        }
    }
}
