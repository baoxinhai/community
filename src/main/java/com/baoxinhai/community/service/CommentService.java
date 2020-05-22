package com.baoxinhai.community.service;

import com.baoxinhai.community.mapper.CommentMapper;
import com.baoxinhai.community.mapper.QuestionMapper;
import com.baoxinhai.community.model.Comment;
import com.baoxinhai.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Transactional
    public void insertComment(Comment comment) {
        //插入一级或者二级评论
        commentMapper.insert(comment);
        //表明插入的是对问题的评论
        if(comment.getType()==1){
            Question question=questionMapper.selectByCommentId(comment.getParentId());
            //该问题的评论数加一
            questionMapper.IncCommentCount(question);
        }
    }
}
