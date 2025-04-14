package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private Connection conn = null;

    private static DbConnection instance;

    private DbConnection() {
        try {
            String URL = "jdbc:mysql://localhost:3306/devharvest";
            String USER = "root";
            String PASSWORD = "";
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("connection etablie");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DbConnection getInstance() {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance;
    }


    public Connection getConn() {
        return conn;
    }

}
