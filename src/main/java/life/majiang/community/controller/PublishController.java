package life.majiang.community.controller;

import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by yangwanjun on 2020/3/17
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String publish(@RequestParam("title") String title,
                          @RequestParam("description") String description,
                          @RequestParam("tag") String tag,
                          HttpServletRequest request,
                          Model model) {
        //将页面数据存入model,用于回显
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        //验证用户登录状态
        Cookie[] cookies = request.getCookies();
        User user = null;
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if (user != null && user.getAccountId() != null) {
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }

        if (user == null || user.getAccountId() == null) {
            model.addAttribute("error", "用户未登录，请先进行登录!");
            return "publish";
        }

        //判空
        if ("".equals(title)) {
            model.addAttribute("error", "标题不能为空!");
            return "publish";
        }
        if ("".equals(description)) {
            model.addAttribute("error", "描述内容不能为空!");
            return "publish";
        }
        if ("".equals(tag)) {
            model.addAttribute("error", "标签不能为空!");
            return "publish";
        }

        //验证空后存入数据库
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        question.setCreator(Long.parseLong(user.getAccountId()));
        questionMapper.insertQuestion(question);
        return "redirect:/";
    }


}
