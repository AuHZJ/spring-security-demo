package com.au.service;

import com.au.model.DTO.UserDTO;

/**
 * @author: huzhijin
 * @create: 2020/4/2 10:51 上午
 **/
public interface UserService {
    UserDTO getUserById(Integer userId);
}
