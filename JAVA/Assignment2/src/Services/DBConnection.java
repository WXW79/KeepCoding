package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;
    private static final String DSN =
            "jdbc:sqlite:D:\\4th_SLC\\JAVA\\Assignment1\\resources\\library.sqlite";

    private DBConnection() {
        try {
 //         System.out.println("log-current path：" + System.getProperty("user.dir"));
            connection = DriverManager.getConnection(DSN);
            System.out.println("Connected to database!");
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Failed to connect to database!-1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
