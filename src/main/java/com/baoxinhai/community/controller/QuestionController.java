package com.baoxinhai.community.controller;

import com.baoxinhai.community.dto.CommentQueryDTO;
import com.baoxinhai.community.dto.QuestionDTO;
import com.baoxinhai.community.model.Question;
import com.baoxinhai.community.service.CommentService;
import com.baoxinhai.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(value = "id") Integer id,
                           Model model){
        QuestionDTO questionDTO=questionService.getQuestionById(id);
        List<Question> relatedQuestion=questionService.selectRelated(questionDTO);
        List<CommentQueryDTO> commentsDTO=commentService.listByQuestionId(id);

        //累加阅读数
        questionService.IncView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("commentsDTO",commentsDTO);
        model.addAttribute("relatedQuestion",relatedQuestion);
        return "question";
    }

}
