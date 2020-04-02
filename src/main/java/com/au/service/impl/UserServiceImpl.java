package com.au.service.impl;

import com.au.dao.UserMapper;
import com.au.model.DO.UserDO;
import com.au.model.DTO.UserDTO;
import com.au.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author: huzhijin
 * @create: 2020/4/2 10:52 上午
 **/
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO user = userMapper.getUserByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("用户不存在");
        }
        return user;
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        UserDO user = userMapper.getUserById(userId);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
