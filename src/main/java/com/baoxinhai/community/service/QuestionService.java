package com.baoxinhai.community.service;

import com.baoxinhai.community.dto.QuestionDTO;
import com.baoxinhai.community.mapper.QuestionMapper;
import com.baoxinhai.community.mapper.UserMapper;
import com.baoxinhai.community.model.Question;
import com.baoxinhai.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    
    public List<QuestionDTO> findAllQuestions() {
        List<Question> allQuestions = questionMapper.findAllQuestions();
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for(Question question:allQuestions){
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
