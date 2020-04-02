package com.au.controller;

import com.au.constant.ResponseCodeEnum;
import com.au.model.DTO.HttpResponseDTO;
import com.au.util.HttpResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: huzhijin
 * @create: 2020/4/2 10:50 上午
 **/
@RestController
@RequestMapping("/authentication")
@Slf4j
public class AuthenticationController {
    @PostMapping("/login")
    public void login(String username, String password) {
    }

    @PostMapping("logout")
    public void logout() {
    }

    @RequestMapping("/hintNotLogin")
    public HttpResponseDTO hintNotLogin() {
        return HttpResponseUtil.failure(ResponseCodeEnum.UNAUTHORIZED_CODE, false);
    }
}
