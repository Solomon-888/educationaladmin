package service;

import dao.StudentDao;
import entity.Grade;
import entity.SelectCourse;
import entity.Student;
import entity.TeachingClass;

import java.util.List;

public class StudentService {
    public Student getStudent(String id) {
        return new StudentDao().getStudent(id);
    }

    public List<Grade> getGrade(String id, String year) {
        return new StudentDao().getGrade(id, year);
    }

    public List<String> getYear() {
        return new StudentDao().getYear();
    }

    public List<SelectCourse> getSelectcourse(String studentId, String cp_year) {
        return new StudentDao().getSelectCourse(studentId, cp_year);
    }

    public boolean courseAdd(String teachingClassname, String studentId) {
        return new StudentDao().courseAdd(teachingClassname, studentId);
    }

    public String getOldpassword(String id) {
        return new StudentDao().getOldpassword(id);
    }
    public boolean updatePassword(String newPassword,String id){
        return new StudentDao().updatePassword(newPassword, id);
    }
    public List<TeachingClass> getTeachingclass(String id, String year){
        return new StudentDao().getTeachingclass(id, year);
    }
    public boolean isConflict(String id,String year,String course){
        return new StudentDao().isConflict(id, year, course);
    }
}
