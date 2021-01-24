package dao;

import entity.User;
import tool.JDBCTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    /**
     * 判断用户ID和密码是否正确
     * @param user
     * @return
     */
    public boolean judgementIDandPassword(User user) {
        Connection connection = JDBCTool.getConnection();
        String sql = "SELECT *FROM s_user WHERE u_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getuId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    String rightPassword = resultSet.getString(2);
                    if (rightPassword.equals(user.getuPassword()))
                        return true;
                    else
                        return false;
                }
            } else
                return false;
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * 获取用户的身份
     * @param user
     * @return
     */
    public String getStatus(User user) {
        if (judgementIDandPassword(user)) {
            Connection connection = JDBCTool.getConnection();
            String sql = "SELECT *FROM s_user WHERE u_id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, user.getuId());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    user.setuStatus(resultSet.getString(3));
                }
                return user.getuStatus();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

}
