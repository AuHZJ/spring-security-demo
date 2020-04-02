package com.au.dao;

import com.au.model.DO.UserDO;

public interface UserMapper {
    UserDO getUserById(Integer userId);

    UserDO getUserByUsername(String username);

    UserDO getUserByPhone(String phone);

    Integer createUser(UserDO userDO);

}
