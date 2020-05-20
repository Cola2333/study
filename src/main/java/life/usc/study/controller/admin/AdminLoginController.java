package life.usc.study.controller.admin;

import life.usc.study.model.Admin;
import life.usc.study.service.admin.AdminLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    AdminLoginService adminLoginService;


    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @PostMapping("login")
    public String login(@RequestParam("userName") String username,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpServletRequest request){
        if (StringUtils.isEmpty(verifyCode)) {
            request.getSession().setAttribute("errorMsg", "验证码不能为空");
            return "admin/login";
        }
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            request.getSession().setAttribute("errorMsg", "用户名或密码不能为空");
            return "admin/login";
        }
        String kaptchaCode = request.getSession().getAttribute("verifyCode") + ""; //使其变为字符串
        if(StringUtils.isEmpty(verifyCode) || !kaptchaCode.equals(verifyCode)) {
            request.getSession().setAttribute("errorMsg", "");
            return "admin/login";
        }

        Admin adminUser = adminLoginService.login(username, password);
        if (adminUser != null) {
            request.getSession().setAttribute("loginUser", adminUser.getNickName());
            request.getSession().setAttribute("loginUserId", adminUser.getId());
            return "redirect:/admin/index";
        }
        else {
            request.getSession().setAttribute("errorMsg", "用户名或密码错误");
            return "admin/login";
        }
    }

}
