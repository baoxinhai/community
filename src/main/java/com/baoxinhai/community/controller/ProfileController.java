package com.baoxinhai.community.controller;

import com.baoxinhai.community.dto.PaginationDTO;
import com.baoxinhai.community.mapper.UserMapper;
import com.baoxinhai.community.model.User;
import com.baoxinhai.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "limit", defaultValue = "5") Integer limit){
        User user=(User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        if(action.equals("questions")){
            model.addAttribute("Section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if(action.equals("replies")){
            model.addAttribute("Section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO allQuestionsById = questionService.findAllQuestionsById(user.getAccountId(), page, limit);
        model.addAttribute("pagination",allQuestionsById);
        return "profile";
    }
}
