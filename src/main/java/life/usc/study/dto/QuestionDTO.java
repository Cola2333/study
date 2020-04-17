package life.usc.study.dto;

import life.usc.study.moel.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private  String description;
    private Long gmtCreate;
    private Long gmtModified;
    private String creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
