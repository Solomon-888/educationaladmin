package entity;

public class Grade {
    private String s_id;
    private String s_name;
    private String course_name;
    private Double g_ordinary;
    private Double g_final;
    private Double g_sum;

    public Grade() {
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

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Double getG_ordinary() {
        return g_ordinary;
    }

    public void setG_ordinary(Double g_ordinary) {
        this.g_ordinary = g_ordinary;
    }

    public Double getG_final() {
        return g_final;
    }

    public void setG_final(Double g_final) {
        this.g_final = g_final;
    }

    public Double getG_sum() {
        return g_sum;
    }

    public void setG_sum(Double g_sum) {
        this.g_sum = g_sum;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "s_id='" + s_id + '\'' +
                ", s_name='" + s_name + '\'' +
                ", course_name='" + course_name + '\'' +
                ", g_ordinary=" + g_ordinary +
                ", g_final=" + g_final +
                ", g_sum=" + g_sum +
                '}';
    }
}
