package service;

import dao.TeacherMapper;
import entity.Grade;
import entity.Teacher;
import entity.TeachingPlan;
import org.apache.ibatis.session.SqlSession;
import tool.MybatisTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherService {
    /**
     * 获取教师实体服务
     * @param t_id
     * @return
     */
    public Teacher getTeacher(String t_id){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = mapper.getTeacher(t_id);
        sqlSession.close();
        return teacher;
    }

    /**
     * 获取教师课程安排服务
     * @param t_id
     * @param year
     * @return
     */
    public List<TeachingPlan> getTeachingplan(String t_id, String year){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Map<String,Object> map = new HashMap<>();
        map.put("t_id",t_id);
        map.put("cp_year",year);
        List<TeachingPlan> teachingplan = mapper.getTeachingplan(map);
        sqlSession.close();
        return teachingplan;
    }

    /**
     * 获取对应班级的学生成绩
     * @param tc_name
     * @return
     */
    public List<Grade> getGradebyClass(String tc_name){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Grade> gradebyClass = mapper.getGradebyClass("java 1班");
        sqlSession.close();
        return gradebyClass;
    }

    /**
     * 更新学生成绩
     * @param grades
     * @param className
     * @return
     */
    public boolean updateGrade(List<Grade> grades, String className){
        SqlSession sqlSession = MybatisTools.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        String teachingclassId = mapper.getTeachingclassIdbyName(className);
        Map<String,Object> map = new HashMap<>();
        map.put("tcId",teachingclassId);
        for (Grade grade:grades){
            map.put("sId",grade.getS_id());
            map.put("ordinary",grade.getG_ordinary());
            map.put("final",grade.getG_final());
            map.put("sum",grade.getG_sum());
            mapper.updateGrade(map);
            sqlSession.commit();
        }
        sqlSession.close();
        return true;
    }
}
