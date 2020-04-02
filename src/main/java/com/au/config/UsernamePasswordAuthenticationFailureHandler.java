package com.au.config;

import com.au.constant.ResponseCodeEnum;
import com.au.model.DTO.HttpResponseDTO;
import com.au.util.HttpResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: huzhijin
 * @create: 2020/4/2 5:42 下午
 **/
@Component
public class UsernamePasswordAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        HttpResponseDTO res = HttpResponseUtil.failure(null, 401, "用户名或密码错误", false);
        response.getWriter().write(new ObjectMapper().writeValueAsString(res));
        response.getWriter().flush();
    }
}
