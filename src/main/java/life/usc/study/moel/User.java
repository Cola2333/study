package life.usc.study.moel;

import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String bio;
    private String avatarUrl;
}
