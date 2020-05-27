package com.baoxinhai.community.controller;

import com.baoxinhai.community.cache.TagCache;
import com.baoxinhai.community.dto.QuestionDTO;
import com.baoxinhai.community.model.Question;
import com.baoxinhai.community.model.User;
import com.baoxinhai.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String editQuestion(@PathVariable("id") Integer id,
                               Model model){
        QuestionDTO questionById = questionService.getQuestionById(id);
        model.addAttribute("title",questionById.getTitle());
        model.addAttribute("description",questionById.getDescription());
        model.addAttribute("tag",questionById.getTag());
        model.addAttribute("id",questionById.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id",required = false) Integer id,
            HttpServletRequest request,
            Model model
    ) {

        model.addAttribute("title", title);
        model.addAttribute("descripton", description);
        model.addAttribute("tag", tag);
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题描述不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error", "输入非法标签"+invalid);
            return "publish";
        }
        User user=(User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录!请先登录，再发布问题");
            return "publish";
        } else {

            Question question = new Question();
            question.setDescription(description);
            question.setTitle(title);
            question.setTag(tag);
            question.setId(user.getId());
            question.setCreator(user.getAccountId());
            question.setId(id);
            questionService.createOrUpdate(question);
            return "redirect:/";
        }
    }
}
