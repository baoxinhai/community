package com.baoxinhai.community.service;

import com.baoxinhai.community.dto.PaginationDTO;
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


    public PaginationDTO findAllQuestions(Integer page, Integer limit) {
        Integer totalCount = questionMapper.count();
        //判断是否越界的情况 做出处理
        Integer totalPage;
        if (totalCount % limit == 0) {
            totalPage = totalCount / limit;
        } else {
            totalPage = totalCount / limit + 1;
        }
        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        //将page做个转换 转换为数据库查询时候需要的offset
        Integer offset = limit * (page - 1);
        List<Question> allQuestions = questionMapper.findAllQuestions(offset, limit);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : allQuestions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        paginationDTO.setPagination(totalCount,page,limit);
        return paginationDTO;
    }
}
