package com.baoxinhai.community.service;

import com.baoxinhai.community.mapper.UserMapper;
import com.baoxinhai.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User userByAccountId = userMapper.findById(user.getAccountId());
        if(userByAccountId==null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            userByAccountId.setAvatarUrl(user.getAvatarUrl());
            userByAccountId.setBio(user.getBio());
            userByAccountId.setName(user.getName());
            userByAccountId.setGmtModified(System.currentTimeMillis());
            userByAccountId.setToken(user.getToken());
            userMapper.updateUserByNewToken(userByAccountId);
        }
    }
}
