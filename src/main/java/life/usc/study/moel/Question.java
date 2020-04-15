package life.usc.study.moel;

import lombok.Data;

@Data
public class Question {
    private int id;
    private String title;
    private  String description;
    private Long gmtCreate;
    private Long gmtModified;
    private String creator;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private String tag;
}
