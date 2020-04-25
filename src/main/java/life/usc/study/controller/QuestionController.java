package life.usc.study.controller;

import life.usc.study.dto.CommentDTO;
import life.usc.study.dto.QuestionDTO;
import life.usc.study.enums.CommentTypeEnum;
import life.usc.study.service.CommentService;
import life.usc.study.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,
                           Model model) {

        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.getRelated(questionDTO);
        List<CommentDTO> commentDTOS = commentService.getByTagetId(id, CommentTypeEnum.QUESTION);
        questionService.incViewCount(id); //阅读数累加
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", commentDTOS);
        model.addAttribute("relatedQuestions", relatedQuestions);

        return "question";
    }
}
