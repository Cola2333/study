package life.usc.study.controller;

import life.usc.study.dto.NotificationDTO;
import life.usc.study.enums.NotificationTypeEnum;
import life.usc.study.model.Notification;
import life.usc.study.model.User;
import life.usc.study.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notification(HttpServletRequest request,
                               @PathVariable("id") Long id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null)
            return "redirect:/";

        Notification notification = notificationService.read(id);
        if (notification.getType() == NotificationTypeEnum.REPLY_COMMENT.getType()
        || notification.getType() == NotificationTypeEnum.REPLY_QUESTION.getType()) {
            return "redirect:/question/" + notification.getOuterId();
        }
        else {
            return "redirect:/";
        }
    }
}
