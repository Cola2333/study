package life.usc.study.dto;

import lombok.Data;

@Data
public class CommentDTO {
    Long parentId;
    Integer type;
    String content;
}
