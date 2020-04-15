package life.usc.study.controller;

import life.usc.study.dto.PaginationDTO;
import life.usc.study.moel.User;
import life.usc.study.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "size", defaultValue = "2") Integer size) {
        if ("question".equals(action)) {
            model.addAttribute("section", action);
            model.addAttribute("sectionName", "我的提问");
        }else if("reply".equals(action)) {
            model.addAttribute("section", action);
            model.addAttribute("sectionName", "我的回复");
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null)
            return "redirect:/";

        PaginationDTO pagination = questionService.list(pageNum, size, user.getAccountId());
        model.addAttribute("questionList", pagination);
        return "profile";
    }
}
