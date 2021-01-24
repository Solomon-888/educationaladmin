package entity;

import lombok.Data;

@Data
public class Grade {
    private String s_id;
    private String s_name;
    private String course_name;
    private Double g_ordinary;
    private Double g_final;
    private Double g_sum;
}
