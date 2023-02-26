package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConfig {

    public Connection getConnection(){
        Connection conn = null;

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://localhost/custemp";
            String username = "root";
            String password = "Sysofni@1021";
            conn  = DriverManager.getConnection(url,username,password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }

    public Statement createStatement(){
        Connection conn = getConnection();
        try {
            return conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
