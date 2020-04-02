package com.au.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author: huzhijin
 * @create: 2020/4/2 5:43 下午
 **/
public class JwtTokenUtil {
    /**
     * 前端请求头的token名
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * 密钥key
     */
    private static final String SECRET = "auhzj";

    /**
     * JWT的发行人
     */
    private static final String ISS = "Au";

    /**
     * 自定义用户信息
     */
    private static final String ROLE_CLAIMS = "role";

    /**
     * 过期时间是3600秒，即是1个小时
     */
    public static final long EXPIRATION = 3600L * 1000;

    /**
     * 选择了记住我之后的过期时间为7天
     */
    public static final long EXPIRATION_REMEMBER = 604800L * 1000;

    /**
     * 创建token
     * @param details
     * @param isRememberMe
     * @return
     * @throws Exception
     */
    public static String createToken(UserDetails details, boolean isRememberMe) throws Exception {
        // 如果选择记住我，则token的过期时间为
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;

        HashMap<String, Object> map = new HashMap<>();

        map.put(ROLE_CLAIMS, details.getAuthorities()); // 角色名字
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, SECRET) // 加密算法
                .setClaims(map) // 自定义信息
                .setIssuer(ISS) // jwt发行人
                .setSubject(details.getUsername()) // jwt面向的用户
                .setIssuedAt(new Date()) // jwt发行时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // key过期时间
                .compact();
    }

    /**
     * 从token获取用户信息
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) throws Exception {
        return getTokenBody(token).getSubject();
    }

    /**
     * 从token中获取用户角色
     *
     * @param token
     * @return
     */
    public static Set<String> getUserRole(String token) throws Exception {
        List<GrantedAuthority> userAuthorities = (List<GrantedAuthority>) getTokenBody(token).get(ROLE_CLAIMS);
        return AuthorityUtils.authorityListToSet(userAuthorities);
    }

    /**
     * 是否已过期
     *
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) throws Exception {
        return getTokenBody(token).getExpiration().before(new Date());
    }

    private static Claims getTokenBody(String token) throws Exception {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * 验证token
     *
     * @param token
     * @param userDetails
     * @return
     */
    public static boolean validateToken(String token, UserDetails userDetails) throws Exception {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && isExpiration(token) == false);
    }
}
