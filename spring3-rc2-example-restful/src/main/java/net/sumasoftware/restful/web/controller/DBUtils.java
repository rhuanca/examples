package net.sumasoftware.restful.web.controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author Renan Huanca
 * @since Nov 29, 2009 10:44:32 AM
 */
public class DBUtils {
    public static void printRestulSet(ResultSet resultSet) throws SQLException {
        int maxrorws = 5;
        ResultSetMetaData metadata = resultSet.getMetaData();
        int i = 0;
        while(resultSet.next() && i < maxrorws){
            System.out.println("row:"+i);
            for(int j=1;j<=metadata.getColumnCount();j++){
                System.out.println(metadata.getColumnName(j)+": "+resultSet.getString(j));
            }
            i++;
        }

    }
}
