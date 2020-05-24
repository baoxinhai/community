package com.baoxinhai.community.service;

import com.baoxinhai.community.dto.CommentQueryDTO;
import com.baoxinhai.community.mapper.CommentMapper;
import com.baoxinhai.community.mapper.QuestionMapper;
import com.baoxinhai.community.mapper.UserMapper;
import com.baoxinhai.community.model.Comment;
import com.baoxinhai.community.model.Question;
import com.baoxinhai.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insertComment(Comment comment) {
        //插入一级或者二级评论
        commentMapper.insert(comment);
        //表明插入的是对问题的评论
        if (comment.getType() == 1) {
            Question question = questionMapper.selectByCommentId(comment.getParentId());
            //该问题的评论数加一
            questionMapper.IncCommentCount(question);
        }
    }

    //查找每个问题对应的所有一级评论
    public List<CommentQueryDTO> listByQuestionId(Integer id) {
        List<Comment> comments = commentMapper.selectByParentId(id);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        List<CommentQueryDTO> commentQueryDTOList = new ArrayList<>();
        for (Comment comment : comments) {
            User user = userMapper.findById(comment.getCommentator());
            CommentQueryDTO commentQueryDTO = new CommentQueryDTO();
            BeanUtils.copyProperties(comment, commentQueryDTO);
            commentQueryDTO.setUser(user);
            commentQueryDTOList.add(commentQueryDTO);
        }
//        }
//        Set<String> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
//        List<String> userAccounts=new ArrayList<>();
//        userAccounts.addAll(commentators);
//
        return commentQueryDTOList;
    }

}
