package service;

import dao.StudentMapper;
import entity.Grade;
import entity.SelectCourse;
import entity.Student;
import entity.TeachingClass;
import org.apache.ibatis.session.SqlSession;
import tool.MybatisTools;

import java.util.*;

public class StudentService {
    public Student getStudent(String id) {
        SqlSession sqlSession = MybatisTools.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.getStudent(id);
        sqlSession.close();
        return student;
    }

    public List<Grade> getGrade(String id, String year) {
        SqlSession sqlSession = MybatisTools.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Map<String, Object> map = new HashMap();
        map.put("id", id);
        map.put("cpYear", year);
        List<Grade> gradeList = mapper.getGrade(map);
        sqlSession.close();
        return gradeList;
    }

    public List<String> getYear() {
        SqlSession sqlSession = MybatisTools.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<String> year = mapper.getYear();
        sqlSession.close();
        if (year != null)
            return year;
        return null;
    }

    public List<SelectCourse> getSelectcourse(String studentId, String cp_year) {
        String id = studentId.substring(0, 2);
        SqlSession sqlSession = MybatisTools.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<SelectCourse> selectCourse = mapper.getSelectCourse(cp_year);
        for (int i = 0; i < selectCourse.size(); i++) {
            if (!selectCourse.get(i).getCpClass().equals(id))
                selectCourse.remove(i);
        }
        return selectCourse;
    }

    public boolean courseAdd(String teachingClassname, String studentId) {
        SqlSession sqlSession = MybatisTools.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        String teachingclassId = mapper.getTeachingclassIdbyName(teachingClassname);
        boolean res = mapper.courseAdd(studentId, teachingclassId);
        sqlSession.commit();
        sqlSession.close();
        if (res) {
            return true;
        }
        return false;
    }

    public String getOldpassword(String id) {
        SqlSession sqlSession = MybatisTools.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        String oldpassword = mapper.getOldpassword(id);
        sqlSession.close();
        return oldpassword;
    }

    public boolean updatePassword(String newPassword, String id) {
        SqlSession sqlSession = MybatisTools.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        boolean b = mapper.updatePassword(newPassword, id);
        sqlSession.commit();
        if (b)
            return true;
        return false;
    }

    public List<TeachingClass> getTeachingclass(String id, String year) {
        SqlSession sqlSession = MybatisTools.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("s_id", id);
        map.put("cp_year", year);
        List<String> teachingClassId = mapper.getTeachingClassId(map);
        List<TeachingClass> teachingClassList = new ArrayList<>();
        for (String tci : teachingClassId) {
            teachingClassList.add(mapper.getTeachingclass(tci));
        }
        sqlSession.close();
        return teachingClassList;
    }

    public boolean isConflict(String id, String year, String course) {
        List<TeachingClass> teachingclass = getTeachingclass(id, year);
        String[] s = course.split(" ");
        for (int i = 0; i < teachingclass.size(); i++) {
            for (int j = 0; j < s.length; j++) {
                if (teachingclass.get(i).getTc_time().contains(s[j]))
                    return true;
            }
        }
        return false;
    }
}
