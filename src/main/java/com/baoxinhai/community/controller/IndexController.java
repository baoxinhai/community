package com.baoxinhai.community.controller;

import com.baoxinhai.community.dto.QuestionDTO;
import com.baoxinhai.community.mapper.QuestionMapper;
import com.baoxinhai.community.mapper.UserMapper;
import com.baoxinhai.community.model.Question;
import com.baoxinhai.community.model.User;
import com.baoxinhai.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }

        List<QuestionDTO> questionList = questionService.findAllQuestions();
        model.addAttribute("questionList", questionList);
        return "index";
    }
}
