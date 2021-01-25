package dao;

import entity.Grade;
import entity.Teacher;
import entity.TeachingPlan;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;
import java.util.Map;

public interface TeacherMapper {
    /*获取教师实体*/
    Teacher getTeacher(String t_id);
    /*获取教师课程安排*/
    List<TeachingPlan> getTeachingplan(Map<String,Object>map);
    /*获取班级成绩*/
    List<Grade> getGradebyClass(String tc_name);
    @Select("SELECT tc_id FROM teaching_class WHERE tc_name = #{tc_name}")
    String getTeachingclassIdbyName(@Param("tc_name") String tc_name);
    /*更新成绩*/
    boolean updateGrade(Map<String,Object> map);
}
