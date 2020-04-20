package life.usc.study.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    Long parentId;
    Integer type;
    String content;
}
