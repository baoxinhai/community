package com.baoxinhai.community.controller;

import com.baoxinhai.community.dto.CommentDTO;
import com.baoxinhai.community.dto.CommentQueryDTO;
import com.baoxinhai.community.dto.ResultDTO;
import com.baoxinhai.community.model.Comment;
import com.baoxinhai.community.model.User;
import com.baoxinhai.community.responceResult.Result;
import com.baoxinhai.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return Result.errorof(2001, "回复操作需要登录，请登录后重试~~");
        }
        if(commentDTO==null|| StringUtils.isBlank(commentDTO.getContent())){
            return Result.errorof(2002,"回复内容不能为空~");
        }

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setCommentator(user.getAccountId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0);
        commentService.insertComment(comment);
        return comment;
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET  )
    public Result<List<CommentQueryDTO>> comments(@PathVariable("id") Integer id){
        List<CommentQueryDTO> list=commentService.listByCommentId(id);
        return Result.okof(list);
    }
}
