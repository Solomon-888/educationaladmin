package entity;

public class SelectCourse {
    private String courseName;
    private String teacherClass;
    private int credit;
    private String teacherName;
    private String courseTime;
    private String coursePoint;

    public SelectCourse() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherClass() {
        return teacherClass;
    }

    public void setTeacherClass(String teacherClass) {
        this.teacherClass = teacherClass;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCoursePoint() {
        return coursePoint;
    }

    public void setCoursePoint(String coursePoint) {
        this.coursePoint = coursePoint;
    }

    @Override
    public String toString() {
        return "SelectCourse{" +
                "courseName='" + courseName + '\'' +
                ", teacherClass='" + teacherClass + '\'' +
                ", credit=" + credit +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
