package com.alttuel.alttuel.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.alttuel.alttuel.config.Token.JwtTokenProvider;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public Optional<UserVO> findUserByUsername(String userid) {
        return userDAO.findUserByUsername(userid);
    }

    public boolean existUser(String userid, String userpassword) {
        if (UserExist(userid) && bCryptPasswordEncoder.matches(userpassword, getPassword(userid))) {
            return true;
        } else {
            return false;
        }

    }

    public boolean UserExist(String userid) {
        return userDAO.UserExist(userid);
    }

    public String getPassword(String userid) {
        return userDAO.getPassword(userid);
    }

    public void createUser(UserVO user) {
        user.setUserpassword(bCryptPasswordEncoder.encode(user.getUserpassword()));
        userDAO.createUser(user);
    }

    public String getToken(UserVO user) {
        return jwtTokenProvider.createToken(user.getUserid(), user.getUserpassword());
    }
}