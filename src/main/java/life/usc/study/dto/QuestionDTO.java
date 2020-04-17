package life.usc.study.dto;

import life.usc.study.moel.User;
import lombok.Data;

@Data
public class QuestionDTO {
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
    private User user;
}
