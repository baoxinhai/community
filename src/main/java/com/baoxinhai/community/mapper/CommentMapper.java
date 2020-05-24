package com.baoxinhai.community.mapper;

import com.baoxinhai.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,commentator,gmt_create,gmt_modified,content,like_count) values(#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{content},#{likeCount})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id=#{id} and type=1")
    List<Comment> selectByParentId(@Param("id") Integer id);
}
