package life.usc.study.controller;

import life.usc.study.dto.PaginationDTO;
import life.usc.study.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                        @RequestParam(name = "size", defaultValue = "2") Integer size) {

        PaginationDTO pagination = questionService.list(pageNum, size);
        model.addAttribute("questionList", pagination); //其实应该叫name应该是pagination 懒得改了


        return "index";
    }
}
