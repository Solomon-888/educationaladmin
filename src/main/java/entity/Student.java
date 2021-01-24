package entity;

import java.util.Date;

public class Student {
    private String s_id;
    private String s_name;
    private String s_sex;
    private Date s_date;
    private String c_id;

    public Student(String s_id, String s_name, String s_sex, Date s_date, String c_id) {
        this.s_id = s_id;
        this.s_name = s_name;
        this.s_sex = s_sex;
        this.s_date = s_date;
        this.c_id = c_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_sex() {
        return s_sex;
    }

    public void setS_sex(String s_sex) {
        this.s_sex = s_sex;
    }

    public Date getS_date() {
        return s_date;
    }

    public void setS_date(Date s_date) {
        this.s_date = s_date;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "s_id='" + s_id + '\'' +
                ", s_name='" + s_name + '\'' +
                ", s_sex='" + s_sex + '\'' +
                ", s_date=" + s_date +
                ", c_id='" + c_id + '\'' +
                '}';
    }
}
