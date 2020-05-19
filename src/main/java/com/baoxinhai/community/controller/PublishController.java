package com.baoxinhai.community.controller;

import com.baoxinhai.community.mapper.QuestionMapper;
import com.baoxinhai.community.mapper.UserMapper;
import com.baoxinhai.community.model.Question;
import com.baoxinhai.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required =false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            HttpServletRequest request,
            Model model
    ) {

        model.addAttribute("title",title);
        model.addAttribute("descripton",description);
        model.addAttribute("tag",tag);
        if(title==null||title==""){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if(description==null||description==""){
            model.addAttribute("error", "问题描述不能为空");
            return "publish";
        }
        if(tag==null||tag==""){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }

        if (user == null) {
            model.addAttribute("error", "用户未登录!请先登录，再发布问题");
            return "publish";
        } else {

            Question question = new Question();
            question.setDescription(description);
            question.setTitle(title);
            question.setTag(tag);
            question.setId(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());

            questionMapper.create(question);
            return "redirect:/";
        }
    }
}
