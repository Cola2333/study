package life.usc.study.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.usc.study.dto.NotificationDTO;
import life.usc.study.dto.PaginationDTO;
import life.usc.study.dto.QuestionDTO;
import life.usc.study.enums.NotificationStatusEnum;
import life.usc.study.enums.NotificationTypeEnum;
import life.usc.study.exception.CustomizeException;
import life.usc.study.exception.CustormizeErrorCode;
import life.usc.study.mapper.NotificationMapper;
import life.usc.study.mapper.QuestionMapper;
import life.usc.study.mapper.UserMapper;
import life.usc.study.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    public PaginationDTO list(Integer pageNum, Integer size, String accountId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(accountId);
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
        Integer totalPage;

        /*
         * 计算totalPage
         * */
        if (totalCount % size == 0) {
            totalPage = totalCount / size; //totalCount是总问题数 size是每页展示的问题数
        }else {
            totalPage = totalCount / size + 1;
        }

        /*
         * 防止页面数超过总页面数 重新给pageNum赋值
         **/
        if (pageNum > totalPage) {
            pageNum = totalPage;
        }else if (pageNum < 1){
            pageNum = 1;
        }

        Integer offset = (pageNum - 1) * size;// limit子句中的第一个参数

        /* 查出当前用户所有notification */
        NotificationExample example = new NotificationExample();
        example.setOrderByClause("gmt_create desc"); //倒序排列
        example.createCriteria()
                .andReceiverEqualTo(accountId);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        /* 拿到所有notifier */
        List<String> userId = notifications.stream().map(notification -> notification.getNotifier()).distinct().collect(Collectors.toList());
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdIn(userId);
        List<User> users = userMapper.selectByExample(userExample);
        //生成usermap
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccountId(), user -> user));

        /* 拿到所有被回复的question */
//        List<Question> questions = new ArrayList<>();
//        for (Notification notification :notifications) {
//            if (notification.getType() == NotificationTypeEnum.REPLY_QUESTION.getType() || notification.getType() == NotificationTypeEnum.REPLY_COMMENT.getType()) { // 如果有if 怎么用java8写
//                Question question = questionMapper.selectByPrimaryKey(notification.getOuterId());
//                questions.add(question);
//            }
//        }
        List<Long> questionId = notifications.stream().map(notification -> notification.getOuterId()).distinct().collect(Collectors.toList()); //如果有if怎么写
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().
                andIdIn(questionId);
        List<Question> questions = questionMapper.selectByExample(questionExample);
        // 生成questionMap
        Map<Long, Question> questionMap = questions.stream().distinct().collect(Collectors.toMap(question -> question.getId(), question -> question));

        /* 生成notificationDTO */
        List<NotificationDTO> notificationDTOS = notifications.stream().map(notification -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setType(NotificationTypeEnum.nameOfType(notification.getType())); // 感觉很奇怪
            notificationDTO.setNotifierName(userMap.get(notification.getNotifier()).getName());
            notificationDTO.setOuterTitle(questionMap.get(notification.getOuterId()).getTitle());
            return notificationDTO;
        }).collect(Collectors.toList());

        /*
         * 得到了所需的全部属性 一起赋值
         * */
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        paginationDTO.setPagination(notificationDTOS, pageNum, totalPage);
        return paginationDTO;
    }

    public Long unreadCount(String accountId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().
                andReceiverEqualTo(accountId).
                andStatusEqualTo(0);
        long unreadCount = notificationMapper.countByExample(notificationExample);
        return unreadCount;
    }

    public Notification read(Long id) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustormizeErrorCode.NOTIFICATION_NOT_FOUND);
        }

        notification.setStatus(NotificationStatusEnum.READE.getStatus()); //标记为已读
        notificationMapper.updateByPrimaryKey(notification);
        return notification;
    }


}
