package dao;


import entity.Grade;
import entity.SelectCourse;
import entity.Student;
import entity.TeachingClass;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface StudentMapper {
    Student getStudent(String id);
    List<String> getYear();
    List<String> getTeachingClassId(Map<String,Object> map);
    TeachingClass getTeachingclass(String tc_id);
    List<SelectCourse> getSelectCourse(String cp_year);

    @Select("SELECT tc_id FROM teaching_class WHERE tc_name = #{tc_name}")
    String getTeachingclassIdbyName(@Param("tc_name") String tc_name);

    @Insert("INSERT INTO grade VALUES(#{tc_name},#{s_id},NULL,NULL,NULL)")
    boolean courseAdd(@Param("s_id") String s_id,@Param("tc_name") String tc_name);

    List<Grade> getGrade(Map<String,Object> map);

    @Select("SELECT u_password FROM s_user WHERE u_id = #{id}")
    String getOldpassword(@Param("id") String id);

    @Update("UPDATE s_user SET u_password = #{newPassword} WHERE u_id = #{id}")
    boolean updatePassword(@Param("newPassword") String newPassword, @Param("id") String id);
}
