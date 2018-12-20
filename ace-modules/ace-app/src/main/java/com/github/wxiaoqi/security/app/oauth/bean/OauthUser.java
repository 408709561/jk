

package com.github.wxiaoqi.security.app.oauth.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 *
 * @author ace
 * @create 2018/3/23.
 */
public class OauthUser extends User implements UserDetails {
    private static final long serialVersionUID = 3123152600328379950L;
    public String id;
    public String name;
    private String tenantId;

    public OauthUser(String id, String username, String password, String name, String tenantId, Collection<GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.name = name;
        this.tenantId = tenantId;
    }

    public OauthUser(String id, String username, String name, String tenantId, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, Collection<GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.name = name;
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
