package life.usc.study.schedule;

import life.usc.study.mapper.QuestionMapper;
import life.usc.study.model.Question;
import life.usc.study.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    QuestionMapper questionMapper;

    @Scheduled(fixedRate = 1000)// 每五秒更新一次
//    @Scheduled(cron = "0 0 1 * * *")// 每天凌晨1点更新
    public void hotTagSchedule() {
        int offset = 0;
        int limit = 5;
        log.info("hotTagSchedule start {}", new Date());
        List<Question> list = new ArrayList<>();
        while (offset == 0 || list.size() == limit) { //可能会有下一页
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : list) {
                log.info("list question: {}", question.getId());
            }
            offset += limit;
        }
        log.info("hotTagSchedule stop {}", new Date());

    }
}
