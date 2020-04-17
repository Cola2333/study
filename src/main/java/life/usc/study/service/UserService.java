package life.usc.study.service;

import life.usc.study.mapper.UserMapper;
import life.usc.study.moel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void insertOrUpdate(User user) {
        User dbUser = userMapper.getUserById(user.getAccountId());
        if (dbUser == null) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
        else {
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setBio(user.getBio());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            userMapper.update(dbUser);
        }
    }
}
