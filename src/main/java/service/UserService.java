package service;

import dao.UserMapper;
import entity.User;
import org.apache.ibatis.session.SqlSession;
import tool.MybatisTools;

public class UserService {
    /**
     * 验证用户ID和密码服务
     *
     * @param user
     * @return
     */

    public boolean judgementIDandPassword(User user) {
        SqlSession sqlSession = MybatisTools.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User userBySql = mapper.getUserById(user.getuId());
        sqlSession.close();
        if (userBySql != null) {
            if (userBySql.getuPassword().equals(user.getuPassword())) {
                user.setuStatus(userBySql.getuStatus());
                return true;
            }
        }
        return false;
    }

}
