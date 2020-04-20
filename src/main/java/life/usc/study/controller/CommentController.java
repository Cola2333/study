package life.usc.study.controller;

import life.usc.study.dto.CommentCreateDTO;
import life.usc.study.dto.ResultDTO;
import life.usc.study.exception.CustormizeErrorCode;
import life.usc.study.model.Comment;
import life.usc.study.model.User;
import life.usc.study.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustormizeErrorCode.NO_LOGIN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setContent(commentCreateDTO.getContent());
        comment.setCommentator(user.getAccountId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
//        commentService.incCommentCount(comment.getParentId()); //直接这样写有bug

        return ResultDTO.okOf();
    }
}
