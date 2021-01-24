import dao.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tool.MybatisTools;

public class UserTest {
    @Test
    public void getUserById(){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.getUserById("1901010101");
        sqlSession.close();
    }
}
