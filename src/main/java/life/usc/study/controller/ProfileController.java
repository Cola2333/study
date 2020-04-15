package life.usc.study.controller;

import life.usc.study.moel.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest request) {
        if ("question".equals(action)) {
            model.addAttribute("section", action);
            model.addAttribute("sectionName", "我的提问");
        }else if("reply".equals(action)) {
            model.addAttribute("section", action);
            model.addAttribute("sectionName", "我的回复");
        }

        User user = (User) request.getSession().getAttribute("user");


        return "profile";
    }
}
