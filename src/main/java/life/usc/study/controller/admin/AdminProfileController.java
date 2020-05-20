package life.usc.study.controller.admin;

import life.usc.study.model.Admin;
import life.usc.study.service.admin.AdminLoginService;
import life.usc.study.service.admin.AdminProfileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminProfileController {
    @Autowired
    AdminProfileService adminProfileService;

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Integer loginUserId = (int)request.getSession().getAttribute("loginUserId");
        Admin adminUser = adminProfileService.getAdminUser(loginUserId);
        if (adminUser == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("loginUserName", adminUser.getName());
        request.setAttribute("nickName", adminUser.getNickName());
        return "admin/profile";
    }

    @ResponseBody
    @PostMapping("/profile/name")
    String updateName(@RequestParam("loginUserName") String loginUserName,
                      @RequestParam("nickName") String nickName,
                      HttpServletRequest request) {

        Integer loginUserId =(int) request.getSession().getAttribute("loginUserId");
        if (adminProfileService.updateName(loginUserId, loginUserName, nickName)) {
            request.getSession().setAttribute("loginUser", loginUserName); //sidebar中的信息是从session中取的 所以要修改session
            request.getSession().setAttribute("nickName", nickName);
            return "success";
        }
        else {
            return "修改失败";
        }
    }

    @ResponseBody
    @PostMapping("/profile/password")
    String updatePassword(@RequestParam("originalPassword") String originalPassword,
                          @RequestParam("newPassword") String newPassword,
                          HttpServletRequest request) {
        if (StringUtils.isBlank(originalPassword) || StringUtils.isBlank(newPassword)) {
            return "参数不能为空";
        }
        Integer loginUserId = (int)request.getSession().getAttribute("loginUserId");
        if (adminProfileService.updatePassword(loginUserId, originalPassword, newPassword)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("nickName");
            return "success";
        }
        else {
            return  "修改失败";
        }
    }
}
