package life.usc.study.service;

import life.usc.study.dto.CommentDTO;
import life.usc.study.enums.CommentTypeEnum;
import life.usc.study.exception.CustomizeException;
import life.usc.study.exception.CustormizeErrorCode;
import life.usc.study.mapper.CommentMapper;
import life.usc.study.mapper.QuestionExtMapper;
import life.usc.study.mapper.QuestionMapper;
import life.usc.study.mapper.UserMapper;
import life.usc.study.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustormizeErrorCode.TARGET_PARAM_NOT_FOUND); //会跳转一个错误页面
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustormizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) { //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId()); //查找comment的父级评论
            if (dbComment == null) {
                throw new CustomizeException(CustormizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

        }else { //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustormizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            incCommentCount(question.getId());
        }
    }


    public void incCommentCount(Long parentId) {
        Question question = new Question();
        question.setId(parentId);
        questionExtMapper.incCommentCount(question);
    }

    public List<CommentDTO> getByTagetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        /** java8获取去重评论人 */
        List<String> commentors = comments.stream().map(comment -> comment.getCommentator()).distinct().collect(Collectors.toList()); //评论人accountId
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdIn(commentors);
        List<User> users = userMapper.selectByExample(userExample); //根据评论人accountId查出user
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccountId(), user -> user));//生成Map

        /** comment转换成commentDTO */
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));// 设置User属性
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
