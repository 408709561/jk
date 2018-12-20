

package com.github.wxiaoqi.security.app.oauth.service;

import com.github.wxiaoqi.security.app.biz.AppUserBiz;
import com.github.wxiaoqi.security.app.entity.AppUser;
import com.github.wxiaoqi.security.app.oauth.bean.OauthUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class OauthUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserBiz userService;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        if (StringUtils.isBlank(mobile)) {
            throw new UsernameNotFoundException("手机号不为空!");
        }
        AppUser appUser = userService.getUserByMobile(mobile);
        if (appUser == null) {
            throw new UsernameNotFoundException("手机号不存在!");
        }
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new OauthUser(appUser.getId().toString(), appUser.getMobile(), appUser.getPassword(), appUser.getName(), appUser.getTenantId(),
                authorities);
    }
}
