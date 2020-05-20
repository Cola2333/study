package life.usc.study.controller;

import life.usc.study.dto.CommentCreateDTO;
import life.usc.study.dto.CommentDTO;
import life.usc.study.dto.ResultDTO;
import life.usc.study.enums.CommentTypeEnum;
import life.usc.study.exception.CustormizeErrorCode;
import life.usc.study.model.Comment;
import life.usc.study.model.User;
import life.usc.study.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    /*
    * 向数据库添加评论
    * */
    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, // 接受一个json数据转成对象
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustormizeErrorCode.NO_LOGIN);
        }

        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustormizeErrorCode.CONTNET_IS_EMPTY);
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

    /*
    * 显示二级评论
    * */
    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDTO<List> comments(@PathVariable("id") Long id){
        List<CommentDTO> commentsDTOS = commentService.getByTagetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentsDTOS);
    }
}
