/*
import dao.StudentMapper;
import entity.Grade;
import entity.Student;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tool.MybatisTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentTest {
    @Test
    public void getStudent(){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.getStudent("1901010101");
        System.out.println(student);
        sqlSession.close();
    }

    @Test
    public void getYear(){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<String> year = mapper.getYear();
        System.out.println(year);
        sqlSession.close();
    }

    @Test
    public void getTeachingClassId(){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Map<String,Object> map = new HashMap<>();
        map.put("s_id","1901010101");
        map.put("cp_year","20-21学年第一学期");
        List<String> year = mapper.getTeachingClassId(map);
        System.out.println(mapper.getTeachingclass("tc_1001"));
        System.out.println(year);
        sqlSession.close();
    }

    @Test
    public void getSelectCourse(){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        System.out.println(mapper.getTeachingclassIdbyName("3D动画 1班"));
        mapper.courseAdd("1901010101","tc_1004");
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void getGrade(){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Map<String,Object> map = new HashMap();
        map.put("id","1901010101");
        map.put("cpYear","20-21学年第一学期");
        List<Grade> grade = mapper.getGrade(map);
        System.out.println(grade);
        sqlSession.close();
    }
    @Test
    public void getOldpassword(){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        System.out.println(mapper.getOldpassword("1901010101"));
        sqlSession.close();
    }
}
*/
