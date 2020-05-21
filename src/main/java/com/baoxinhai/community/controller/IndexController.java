package com.baoxinhai.community.controller;

import com.baoxinhai.community.dto.PaginationDTO;
import com.baoxinhai.community.mapper.UserMapper;
import com.baoxinhai.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "limit", defaultValue = "5") Integer limit
    ) {

        PaginationDTO pagination = questionService.findAllQuestions(page,limit);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
