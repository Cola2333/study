package life.usc.study.dto;

import life.usc.study.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private String type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
    private Long outerId;
}
