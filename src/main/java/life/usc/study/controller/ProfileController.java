package life.usc.study.controller;

import com.github.pagehelper.PageInfo;
import life.usc.study.dto.NotificationDTO;
import life.usc.study.dto.PaginationDTO;
import life.usc.study.dto.QuestionDTO;
import life.usc.study.model.Notification;
import life.usc.study.model.Question;
import life.usc.study.model.User;
import life.usc.study.service.NotificationService;
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

    @Autowired
    NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "size", defaultValue = "2") Integer size) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null)
            return "redirect:/";

        if ("question".equals(action)) {
            model.addAttribute("section", action);
            model.addAttribute("sectionName", "我的提问");

            PaginationDTO pagination = questionService.list(pageNum, size, user.getAccountId());
            model.addAttribute("questionList", pagination);
        }else if("reply".equals(action)) {
            PaginationDTO pagination = notificationService.list(pageNum, size, user.getAccountId());
//            Long unreadCount = notificationService.unreadCount(user.getAccountId());
            model.addAttribute("questionList", pagination);
//            model.addAttribute("unreadCount", unreadCount);
            model.addAttribute("section", action);
            model.addAttribute("sectionName", "最新回复");
        }

        return "profile";
    }
}
