package life.usc.study.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    @GetMapping({"/", "/index"})
    String index(HttpServletRequest request) {
        request.setAttribute("path" ,"index");
        request.setAttribute("categoryCount", 1);
        request.setAttribute("blogCount", 1);
        request.setAttribute("linkCount", 1);
        request.setAttribute("tagCount", 1);
        request.setAttribute("commentCount", 1);
        request.setAttribute("path", "index");
        return "admin/index";
    }

}
