package com.au.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author: huzhijin
 * @create: 2020/4/2 2:44 下午
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private UsernamePasswordAuthenticationSuccessHandler usernamePasswordAuthenticationSuccessHandler;

    @Autowired
    private UsernamePasswordAuthenticationFailureHandler usernamePasswordAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 放行authentication相关接口
                .antMatchers("/authentication/**").permitAll()
                // 放行swagger相关页面
                .antMatchers("/webjars/**", "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui.html/**").permitAll()
                // 其它接口通过认证后可以访问
                .anyRequest().authenticated();

        http.formLogin()
                // 所有未认证的请求都会到达这个接口
                .loginPage("/authentication/hintNotLogin")
                // 接收登录请求的url
                .loginProcessingUrl("/authentication/login")
                // 登录成功后的处理
                .successHandler(usernamePasswordAuthenticationSuccessHandler)
                // 登录失败后的处理
                .failureHandler(usernamePasswordAuthenticationFailureHandler);

        // 关闭csrf跨站防护
        http.csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService) // 设置用户名密码的来源
                .passwordEncoder(new BCryptPasswordEncoder()); // 登录密码加密及匹配方式
    }
}
