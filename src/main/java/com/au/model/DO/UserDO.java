package com.au.model.DO;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: huzhijin
 * @create: 2020/4/2 10:54 上午
 **/
@Data
public class UserDO implements UserDetails {

    private Integer id;

    private String username;

    private String password;

    private Integer sex;

    private String phone;

    private List<RoleDO> roles;

    private Integer del;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles == null ? Collections.EMPTY_LIST : roles.parallelStream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
