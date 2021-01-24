package tool;

import java.sql.*;
import java.util.ResourceBundle;

public class JDBCTool {
    private static String username;
    private static String password;
    private static String driver;
    private static String url;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("JDBC");
        username = resourceBundle.getString("username");
        password = resourceBundle.getString("password");
        url = resourceBundle.getString("url");
        driver = resourceBundle.getString("driver");
    }

    public static Connection getConnection() {
        try {
            //注册驱动
            Class.forName(driver);
            //获取链接
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
