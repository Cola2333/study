package life.usc.study.dto;

import lombok.Data;

@Data
public class QuestionQueryDTO {
    private Integer offset;
    private Integer size;
    private String search;
    private String topHot;
}
