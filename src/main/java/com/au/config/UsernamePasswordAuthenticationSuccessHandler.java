package com.au.config;

import com.au.constant.ResponseCodeEnum;
import com.au.dao.UserMapper;
import com.au.model.DO.UserDO;
import com.au.model.DTO.UserDTO;
import com.au.util.HttpResponseUtil;
import com.au.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: huzhijin
 * @create: 2020/4/2 5:30 下午
 **/
@Component
public class UsernamePasswordAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDO user = userMapper.getUserByUsername(authentication.getName());
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        try {
            String token = JwtTokenUtil.TOKEN_PREFIX + JwtTokenUtil.createToken(user, false);
            redisTemplate.opsForValue().set(token, user.getUsername());
            userDTO.setToken(token);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(HttpResponseUtil.success(userDTO, 200, "登录成功")));
            response.getWriter().flush();
        } catch (Exception e) {
            response.getWriter().write(new ObjectMapper().writeValueAsString(HttpResponseUtil.failure(ResponseCodeEnum.UNAUTHORIZED_CODE, false)));
        }
    }
}
