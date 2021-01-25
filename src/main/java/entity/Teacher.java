package entity;

import lombok.Data;

@Data
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

}
