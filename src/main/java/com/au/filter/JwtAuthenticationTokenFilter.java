package com.au.filter;

import com.au.util.HttpResponseUtil;
import com.au.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: huzhijin
 * @create: 2020/4/2 5:31 下午
 **/
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserDetailsService userDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private String tokenPrefix = "TOKEN_";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        if (token != null && StringUtils.startsWith(token, JwtTokenUtil.TOKEN_PREFIX)) {
            token = StringUtils.substring(token, JwtTokenUtil.TOKEN_PREFIX.length());
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String username = JwtTokenUtil.getUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailService.loadUserByUsername(username);

                // 从redis判断是否有该token
                Object o = redisTemplate.opsForValue().get(tokenPrefix + JwtTokenUtil.TOKEN_PREFIX + token);
                if (o == null || !username.equals(o)) {
                    throw new RuntimeException("token已失效，请重新登录");
                }

                if (JwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        } catch (Exception e) {
            log.error("token验证出错，{}", e.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(HttpResponseUtil.failure(null, 401, "token已失效，请重新登录", false)));
            return;
        }

        filterChain.doFilter(request, response);
    }
}
