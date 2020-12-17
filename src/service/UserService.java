package service;

import dao.UserDao;
import entity.User;

public class UserService {
    /**
     * 验证用户ID和密码服务
     * @param user
     * @return
     */

    public boolean judgementIDandPassword(User user){
        return new UserDao().judgementIDandPassword(user);
    }

    /**
     * 获取用户身份服务
     * @param user
     * @return
     */
    public String getStatus(User user){
        return new UserDao().getStatus(user);
    }
}
