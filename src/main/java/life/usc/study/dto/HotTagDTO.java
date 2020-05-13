package life.usc.study.dto;

import lombok.Data;

@Data
/*
* 用于向PriorityQueue传参
* */
public class HotTagDTO {
    private String name;
    private Integer priority;
}
