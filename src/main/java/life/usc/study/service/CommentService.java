package life.usc.study.service;

import life.usc.study.enums.CommentTypeEnum;
import life.usc.study.exception.CustomizeException;
import life.usc.study.exception.CustormizeErrorCode;
import life.usc.study.mapper.CommentMapper;
import life.usc.study.mapper.QuestionExtMapper;
import life.usc.study.mapper.QuestionMapper;
import life.usc.study.model.Comment;
import life.usc.study.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

@Service
public class CommentService {
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @Autowired
    CommentMapper commentMapper;

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
}
