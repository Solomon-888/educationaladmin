package dao;

import entity.Grade;
import entity.Teacher;
import entity.TeachingPlan;
import tool.JDBCTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDao {
    /**
     * 获取teacher实体
     *
     * @param t_id
     * @return
     */
    public Teacher getTeacher(String t_id) {
        Teacher teacher = new Teacher();
        String sql = "SELECT *FROM teacher WHERE t_id = ?";
        Connection connection = JDBCTool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, t_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                teacher.setT_id(resultSet.getString(1));
                teacher.setT_name(resultSet.getString(2));
                teacher.setT_sex(resultSet.getString(3));
                teacher.setT_date(resultSet.getString(4));
            }
            JDBCTool.closeConnection(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return teacher;
    }

    /**
     * 获取教师计划表
     *
     * @param t_id
     * @param year
     * @return
     */
    public List<TeachingPlan> getTeachingplan(String t_id, String year) {
        String sql = "SELECT t_id,tc_name,tc_time,tc_point,cp_year FROM teacher_teaching_class \n" +
                "JOIN teaching_class ON teacher_teaching_class.tc_id = teaching_class.tc_id\n" +
                "JOIN teaching_plan ON teacher_teaching_class.tc_id = teaching_plan.tc_id\n" +
                "WHERE teacher_teaching_class.t_id = ? AND cp_year = ?";
        List<TeachingPlan> teachingPlans = new ArrayList<>();
        Connection connection = JDBCTool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, t_id);
            preparedStatement.setString(2, year);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TeachingPlan teachingPlan = new TeachingPlan();
                teachingPlan.setTc_name(resultSet.getString(2));
                teachingPlan.setTc_time(resultSet.getString(3));
                teachingPlan.setTc_point(resultSet.getString(4));
                teachingPlans.add(teachingPlan);
            }
            JDBCTool.closeConnection(connection);
            return teachingPlans;
        } catch (SQLException throwables) {

        }
        return null;
    }

    /**
     * 查询相应教学班级对应的学生即成绩
     *
     * @param
     */
    public List<Grade> getGradebyClass(String tc_name) {
        String sql = "SELECT student.s_id,student.s_name,g_ordinary,g_final,g_sum FROM grade JOIN student ON grade.s_id = student.s_id \n" +
                "JOIN teaching_class ON teaching_class.tc_id = grade.tc_id\n" +
                "WHERE tc_name = ?";
        List<Grade> gradeList = new ArrayList<>();
        Connection connection = JDBCTool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tc_name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Grade grade = new Grade();
                grade.setS_id(resultSet.getString(1));
                grade.setS_name(resultSet.getString(2));
                double ordinary = resultSet.getDouble(3);
                if (ordinary != 0)
                    grade.setG_ordinary(ordinary);
                double Final = resultSet.getDouble(4);
                if (Final != 0)
                    grade.setG_final(Final);
                double sum = resultSet.getDouble(5);
                if (sum != 0)
                    grade.setG_sum(sum);
                gradeList.add(grade);
            }
            JDBCTool.closeConnection(connection);
            return gradeList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean updateGrade(List<Grade> grades, String className) {
        String getClassid = "SELECT tc_id FROM teaching_class WHERE tc_name = ? ";
        String updateGrade = "UPDATE grade SET g_ordinary = ?,g_final = ?,g_sum = ? \n" +
                "WHERE tc_id = ? AND s_id = ?";
        Connection connection = JDBCTool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getClassid);
            String tc_id = null;
            preparedStatement.setString(1, className);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tc_id = resultSet.getString(1);
            }
            for (Grade g:grades){
                preparedStatement = connection.prepareStatement(updateGrade);
                preparedStatement.setDouble(1,g.getG_ordinary());
                preparedStatement.setDouble(2,g.getG_final());
                preparedStatement.setDouble(3,g.getG_sum());
                preparedStatement.setString(4,tc_id);
                preparedStatement.setString(5,g.getS_id());
                if (preparedStatement.executeUpdate() == 0)
                    return false;
            }
            JDBCTool.closeConnection(connection);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
