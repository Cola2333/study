package life.usc.study.schedule;

import life.usc.study.cache.HotTagCache;
import life.usc.study.mapper.QuestionMapper;
import life.usc.study.model.Question;
import life.usc.study.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
/*
 * 为HotTagCache中的TagsPriorities赋值
 * */
public class HotTagTasks {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    HotTagCache hotTagCache;

    @Scheduled(fixedRate = 10000)// 每五秒更新一次
//    @Scheduled(cron = "0 0 1 * * *")// 每天凌晨1点更新

    /*
    * 为HotTagCache中的TagsPriorities赋值
    * */
    public void hotTagSchedule() {
        int offset = 0;
        int limit = 5;
        log.info("hotTagSchedule start {}", new Date());
        List<Question> list = new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>();
        while (offset == 0 || list.size() == limit) { //可能会有下一页
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : list) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    if (priorities.containsKey(tag)) {
                        priorities.put(tag, priorities.get(tag) + 5 + question.getCommentCount());
                    }
                    else {
                        priorities.put(tag, 5 + question.getCommentCount());
                    }
                }
            }
            offset += limit;
        }
        hotTagCache.setTagsPriorities(priorities);
//        priorities.forEach((name, priority) -> {
//            System.out.print(name);
//            System.out.print(":");
//            System.out.print(priority);
//            System.out.println("");
//        });
        hotTagCache.updateTagsPriorities(priorities);

        log.info("hotTagSchedule stop {}", new Date());

    }
}
