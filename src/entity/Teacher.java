package entity;

public class Teacher {
    private String t_id;
    private String t_name;
    private String t_sex;
    private String t_date;

    public Teacher(String t_id, String t_name, String t_sex, String t_date) {
        this.t_id = t_id;
        this.t_name = t_name;
        this.t_sex = t_sex;
        this.t_date = t_date;
    }

    public Teacher() {
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_sex() {
        return t_sex;
    }

    public void setT_sex(String t_sex) {
        this.t_sex = t_sex;
    }

    public String getT_date() {
        return t_date;
    }

    public void setT_date(String t_date) {
        this.t_date = t_date;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "t_id='" + t_id + '\'' +
                ", t_name='" + t_name + '\'' +
                ", t_sex='" + t_sex + '\'' +
                ", t_date='" + t_date + '\'' +
                '}';
    }

}
