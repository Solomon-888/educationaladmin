package service;

import dao.TeacherDao;
import entity.Grade;
import entity.Teacher;
import entity.TeachingPlan;

import java.util.List;

public class TeacherService {
    /**
     * 获取教师实体服务
     * @param t_id
     * @return
     */
    public Teacher getTeacher(String t_id){
        return new TeacherDao().getTeacher(t_id);
    }

    /**
     * 获取教师课程安排服务
     * @param t_id
     * @param year
     * @return
     */
    public List<TeachingPlan> getTeachingplan(String t_id, String year){
        return new TeacherDao().getTeachingplan(t_id, year);
    }

    /**
     * 获取对应班级的学生成绩
     * @param tc_name
     * @return
     */
    public List<Grade> getGradebyClass(String tc_name){
        return new TeacherDao().getGradebyClass(tc_name);
    }

    /**
     * 更新学生成绩
     * @param grades
     * @param className
     * @return
     */
    public boolean updateGrade(List<Grade> grades, String className){
        return new TeacherDao().updateGrade(grades, className);
    }
}
