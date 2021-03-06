package com.baoxinhai.community.mapper;

import com.baoxinhai.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Component
public interface QuestionMapper {

    //插入问题
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
     void create(Question question);

    //找到所有的问题，放在首页上
    @Select("select * from question order by gmt_create desc limit #{offset},#{limit}")
    List<Question> findAllQuestions(@Param(value = "offset") Integer offset,@Param(value = "limit") Integer limit);

    //计算共有多少个问题
    @Select("select count(1) from question")
    Integer count();

    //根据用户的account_id找到其发布的所有问题
    @Select("select * from question where creator=#{account_id} limit #{offset},#{limit}")
    List<Question> findAllQuestionsById(@Param(value = "account_id")String account_id,@Param(value = "offset") Integer offset,@Param(value = "limit") Integer limit);

    //根据用户account_id找到用户对应的问题的条数
    @Select("select count(1) from question where creator=#{account_id}")
    Integer countById(@Param(value = "account_id")String account_id);

    //根据问题id找到相应的问题
    @Select("select * from question where id= #{questionId}")
    Question getQuestionById(@Param(value = "questionId") Integer questionId);

    //更新问题
    @Update("update question set title=#{title},description=#{description},tag=#{tag},gmt_modified=#{gmtModified} where id=#{id}")
    void updateById(Question question);

    //浏览数加一
    @Update("update question set view_count=view_count+1 where id=#{id}")
    void updateViewCountById(Question question);

    @Select("select * from question where id=#{parentId}")
    Question selectByCommentId(Integer parentId);

    //评论数加一
    @Update("update question set comment_count=comment_count+1 where id=#{id}")
    void IncCommentCount(Question question);

    @Select("select * from question where tag REGEXP #{tag} and id !=#{id}")
    List<Question> selectRelated(Question question);

}
