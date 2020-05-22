package com.baoxinhai.community.mapper;

import com.baoxinhai.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,commentor,gmt_create,gmt_modified,content,like_count) values(#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{content},#{likeCount})")
    void insert(Comment comment);
}
