package dao;

import entity.Grade;
import entity.SelectCourse;
import entity.Student;
import entity.TeachingClass;
import tool.JDBCTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDao {

    /**
     * 获取学生实体
     * @param id
     * @return
     */

    public Student getStudent(String id) {
        String sql = "SELECT student.s_id,student.s_name,student.s_sex,student.s_date,class.c_name FROM student JOIN class ON student.c_id = class.c_id WHERE s_id = ?";
        Connection connection = JDBCTool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Student student = null;
            while (resultSet.next()) {
                String s_id = resultSet.getString(1);
                String s_name = resultSet.getString(2);
                String s_sex = resultSet.getString(3);
                Date s_date = resultSet.getDate(4);
                String c_id = resultSet.getString(5);
                student = new Student(s_id, s_name, s_sex, s_date, c_id);
            }
            connection.close();
            return student;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 获取学生成绩
     * @param id
     * @param year
     * @return
     */

    public List<Grade> getGrade(String id, String year) {
        ArrayList<Grade> list = new ArrayList<>();
        String sql = "SELECT *FROM student_grade_year WHERE s_id = ? AND cp_year = ?";
        Connection connection = JDBCTool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, year);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Grade grade = new Grade();
                grade.setS_id(resultSet.getString(1));
                grade.setS_name(resultSet.getString(2));
                grade.setCourse_name(resultSet.getString(3));
                grade.setG_ordinary(resultSet.getDouble(4));
                grade.setG_final(resultSet.getDouble(5));
                grade.setG_sum(resultSet.getDouble(6));
                list.add(grade);
            }
            JDBCTool.closeConnection(connection);
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 获取学年
     *
     * @return
     */
    public List<String> getYear() {
        ArrayList<String> al = new ArrayList<>();
        String sql = "SELECT DISTINCT cp_year FROM course_plan";
        Connection connection = JDBCTool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                al.add(resultSet.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JDBCTool.closeConnection(connection);
        return al;
    }


    /**
     * 获得选课信息
     *
     * @param studentId
     * @param cp_year
     * @return
     */
    public List<SelectCourse> getSelectCourse(String studentId, String cp_year) {
        String id = studentId.substring(0, 2);
        String sql = "SELECT *FROM yc WHERE cp_year = ?";
        Connection connection = JDBCTool.getConnection();
        ArrayList<SelectCourse> al = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cp_year);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String cp_class = resultSet.getString(2);
                //判断与学生年级是否一致
                if (cp_class.equals(id)) {
                    SelectCourse selectCourse = new SelectCourse();
                    selectCourse.setCourseName(resultSet.getString(3));
                    selectCourse.setTeacherName(resultSet.getString(7));
                    selectCourse.setTeacherClass(resultSet.getString(5));
                    selectCourse.setCredit(resultSet.getInt(6));
                    selectCourse.setCourseTime(resultSet.getString(9));
                    selectCourse.setCoursePoint(resultSet.getString(8));
                    al.add(selectCourse);
                }
            }
            JDBCTool.closeConnection(connection);
            return al;
        } catch (SQLException throwables) {
        }
        return null;
    }

    /**
     * 增加选课
     */
    public boolean courseAdd(String teachingClassname, String studentId) {
        String teachingClassid = null;
        String sqlSelectid = "SELECT tc_id FROM teaching_class WHERE tc_name = ?";
        String sqlInsert = "INSERT INTO grade VALUES(?,?,NULL,NULL,NULL)";
        Connection connection = JDBCTool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectid);
            preparedStatement.setString(1, teachingClassname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                teachingClassid = resultSet.getString(1);
            }
            PreparedStatement preparedStatement1 = connection.prepareStatement(sqlInsert);
            preparedStatement1.setString(1, teachingClassid);
            preparedStatement1.setString(2, studentId);
            if (preparedStatement1.executeUpdate() != 0)
                return true;
            else
                return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * 获取用户老密码
     *
     * @param
     */

    public String getOldpassword(String id) {
        Connection connection = JDBCTool.getConnection();
        String sql = "SELECT u_password FROM s_user WHERE u_id = ?";
        String oldPassword = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                oldPassword = resultSet.getString(1);
            }
            JDBCTool.closeConnection(connection);
            return oldPassword;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 更新密码
     *
     * @param
     */
    public boolean updatePassword(String newPassword, String id) {
        String sql = "UPDATE s_user SET u_password = ? WHERE u_id = ?";
        Connection connection = JDBCTool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, id);
            if (preparedStatement.executeUpdate() != 0)
                return true;
            else
                return false;
        } catch (SQLException throwables) {
        }
        return false;
    }

    /**
     * 查询该学期的教学班级详细内容
     *
     * @param
     */
    public List<TeachingClass> getTeachingclass(String id, String year) {
        List<String> tcId = new ArrayList<>();
        List<TeachingClass> teachingClasses = new ArrayList<>();
        Connection connection = JDBCTool.getConnection();
        String getTCid = "SELECT grade.tc_id FROM grade,teaching_plan WHERE grade.tc_id = teaching_plan.tc_id\n" +
                "AND cp_year = ?\n" +
                "AND S_id = ?";
        String getTC = "SELECT teaching_plan.tc_id,tc_name,course_name,tc_time,tc_point,tc_num FROM teaching_class,teaching_plan,course WHERE \n" +
                "teaching_class.tc_id = teaching_plan.tc_id AND \n" +
                "teaching_plan.course_id = course.course_id AND teaching_plan.tc_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getTCid);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tcId.add(resultSet.getString(1));
            }
            for (String str : tcId) {
                preparedStatement = connection.prepareStatement(getTC);
                preparedStatement.setString(1, str);
                ResultSet resultSet1 = preparedStatement.executeQuery();
                while (resultSet1.next()) {
                    TeachingClass teachingClass = new TeachingClass();
                    teachingClass.setTc_id(resultSet1.getString(1));
                    teachingClass.setTc_name(resultSet1.getString(2));
                    teachingClass.setC_name(resultSet1.getString(3));
                    teachingClass.setTc_time(resultSet1.getString(4));
                    teachingClass.setTc_point(resultSet1.getString(5));
                    teachingClass.setTc_num(resultSet1.getString(6));
                    teachingClasses.add(teachingClass);
                }
            }
            JDBCTool.closeConnection(connection);
            return teachingClasses;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 对比学生选课和已有课程是否冲突
     * @param
     */
    public boolean isConflict(String id,String year,String course){
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
