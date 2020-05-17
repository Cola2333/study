package life.usc.study.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.usc.study.cache.HotTagCache;
import life.usc.study.dto.PaginationDTO;
import life.usc.study.model.Question;
import life.usc.study.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name= "search", required = false) String search,
                        @RequestParam(name= "topHot", required = false) String topHot) {


        PaginationDTO pagination = questionService.show(pageNum, size, search, topHot);
        LinkedList<String> topHots = hotTagCache.getTopHots();
        model.addAttribute("questionList", pagination); //其实应该叫name应该是pagination 懒得改了
        model.addAttribute("search", search);
        model.addAttribute("topHots", topHots);
        model.addAttribute("topHot", topHot); //修复换页后不再是topHot的bug

        return "index";
    }
}
