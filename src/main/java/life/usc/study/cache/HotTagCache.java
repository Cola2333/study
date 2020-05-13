package life.usc.study.cache;

import life.usc.study.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
/*
* 用于存储每个Tag的Priority
* */
public class HotTagCache {
    private Map<String, Integer> tagsPriorities = new HashMap<>(); //接受HotTagTasks传过来的所有tag的priority
    private LinkedList<String> topHots = new LinkedList<>(); //存储PriorityQueue中的topHots

    public void updateTagsPriorities(Map<String, Integer> tagsPriorities) {
        topHots = new LinkedList<>(); //防止累加
        int max = 5; //生成最热问题的个数
        PriorityQueue<HotTagDTO> pq = new PriorityQueue<>(3, ((o1, o2) -> o1.getPriority() - o2.getPriority()));
        tagsPriorities.forEach((name, priority) -> {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            pq.add(hotTagDTO);
            if (pq.size() > max) {
                pq.poll();
            }
        });

        while(!pq.isEmpty()) {
            topHots.addFirst(pq.poll().getName());
        }
    }
}

