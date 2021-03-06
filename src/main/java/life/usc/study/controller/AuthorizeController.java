package life.usc.study.controller;

import life.usc.study.dto.AccessTokenDTO;
import life.usc.study.dto.GitHubUser;
import life.usc.study.model.User;
import life.usc.study.provide.GitHubProvider;
import life.usc.study.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    UserService userService;


    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        if (gitHubUser != null) {
            User user = new User();
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setName(gitHubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);//是javaJDK提供的一个自动生成主键的方法。UUID(Universally Unique Identifier)全局唯一标识符,是指在一台机器上生成的数字，它保证对在同一时空中的所有机器都是唯一的，是由一个十六位的数字组成,表现出来的 形式。由以下几部分的组合
            user.setBio(gitHubUser.getBio());
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            userService.insertOrUpdate(user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        }else {
            log.error("callback get github error, {}", gitHubUser);
            //登录失败
            return "redirect:/";
        }
    }

    @GetMapping("/logOut")
    public String logOut(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");

        Cookie cookie = new Cookie("token", null);
        response.addCookie(cookie);
        cookie.setMaxAge(0);
        return "redirect:/";
    }
}
