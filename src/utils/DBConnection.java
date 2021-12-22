package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection(){
        Connection connection = null;
        try {
            String jdbcurl= "jdbc:sqlite:src/resources/test.db";
            connection = DriverManager.getConnection(jdbcurl);
            System.out.println("Connected!");
        } catch (SQLException e ) {
            System.out.println(e);
        }
        return connection;
    }
}
