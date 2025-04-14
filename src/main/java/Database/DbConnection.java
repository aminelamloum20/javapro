package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/devharvest";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    private DbConnection() {
        // private constructor to prevent instantiation
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connected to MySQL!");
            } catch (SQLException e) {
                System.err.println("❌ Failed to connect to database:");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("🔌 Disconnected from MySQL.");
            } catch (SQLException e) {
                System.err.println("❌ Failed to close connection:");
                e.printStackTrace();
            }
        }
    }

}
