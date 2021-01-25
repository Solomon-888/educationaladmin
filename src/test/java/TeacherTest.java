import dao.TeacherMapper;
import entity.Grade;
import entity.Teacher;
import entity.TeachingPlan;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tool.MybatisTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherTest {
    @Test
    public void getTeacher(){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher("t_1001");
        System.out.println(teacher);
        sqlSession.close();
    }
    @Test
    public void getTeachingplan(){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Map<String,Object> map = new HashMap<>();
        map.put("t_id","t_1001");
        map.put("cp_year","20-21学年第一学期");
        List<TeachingPlan> teachingplan = mapper.getTeachingplan(map);
        System.out.println(teachingplan);
        sqlSession.close();
    }

    @Test
    public void getGradebyClass(){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Grade> gradebyClass = mapper.getGradebyClass("java 1班");
        System.out.println(gradebyClass);
        sqlSession.close();
    }

    @Test
    public void getTeachingclassIdbyName(){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        String teachingclassIdbyName = mapper.getTeachingclassIdbyName("java 1班");
        System.out.println(teachingclassIdbyName);
        sqlSession.close();
    }
}
