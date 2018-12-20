

package com.github.wxiaoqi.security.oauth.client.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ace on 2017/8/11.
 */
@Component
public class SysUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if (StringUtils.isBlank(username)) {
//            throw new UsernameNotFoundException("用户名为空");
//        }
        boolean isNotExpired = true, isNotLock = true;
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User("admin", "null",
                true, // 是否可用
                isNotExpired, //, // 未过期为true
                true, // 证书不过期为true
                isNotLock, // 账户未锁定为true
                authorities);
    }
}
