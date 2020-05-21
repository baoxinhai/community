package com.baoxinhai.community.controller;

import com.baoxinhai.community.dto.QuestionDTO;
import com.baoxinhai.community.mapper.QuestionMapper;
import com.baoxinhai.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(value = "id") Integer id,
                           Model model){
        QuestionDTO questionDTO=questionService.getQuestionById(id);
        //累加阅读数
        questionService.IncView(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }

}
