package com.au.controller;

import com.au.model.DTO.HttpResponseDTO;
import com.au.model.DTO.UserDTO;
import com.au.service.UserService;
import com.au.util.HttpResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: huzhijin
 * @create: 2020/4/2 7:16 下午
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/getUserById")
    public HttpResponseDTO getUserById(@RequestParam Integer userId) {
        UserDTO user = userService.getUserById(userId);
        return HttpResponseUtil.success(user);
    }
}
