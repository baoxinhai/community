package com.baoxinhai.community.mapper;

import com.baoxinhai.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Mapper
@Component
public interface UserMapper {

    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,bio,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
     void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where account_id=#{account_id}")
    User findById(@Param("account_id") String account_id);

    @Update("update user set name=#{name},token=#{token},bio=#{bio},avatar_url=#{avatarUrl},gmt_modified=#{gmtModified} where account_id=#{accountId}")
    void updateUserByNewToken(User userByAccountId);
}
