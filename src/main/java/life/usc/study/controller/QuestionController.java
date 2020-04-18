package life.usc.study.controller;

import life.usc.study.dto.QuestionDTO;
import life.usc.study.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id,
                           Model model) {

        QuestionDTO questionDTO = questionService.getById(id);
        questionService.incViewCount(id); //阅读数累加
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
