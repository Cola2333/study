package life.usc.study.controller;

import life.usc.study.dto.CommentDTO;
import life.usc.study.dto.ResultDTO;
import life.usc.study.exception.CustormizeErrorCode;
import life.usc.study.mapper.CommentMapper;
import life.usc.study.model.Comment;
import life.usc.study.model.User;
import life.usc.study.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustormizeErrorCode.NO_LOGIN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setContent(commentDTO.getContent());
        comment.setCommentator(1);
        comment.setLikeCount(0L);
        commentService.insert(comment);
//        commentService.incCommentCount(comment.getParentId()); //直接这样写有bug

        return ResultDTO.okOf();
    }
}
