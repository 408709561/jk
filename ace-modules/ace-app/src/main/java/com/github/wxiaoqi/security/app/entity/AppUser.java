package com.github.wxiaoqi.security.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.wxiaoqi.security.common.validator.group.AddGroup;
import com.github.wxiaoqi.security.common.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @author Mr.AG
 * @version 2018-05-16 20:56:32
 * @email 463540703@qq.com
 */
@Table(name = "app_user")
public class AppUser implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    @Id
    private Integer id;

    //手机号
    @Column(name = "mobile")
    @NotBlank(message = "用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;

    //密码
    @Column(name = "password")
    @NotBlank(message = "密码不能为空", groups = AddGroup.class)
    @JsonIgnore
    private String password;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "is_deleted")
    private String isDeleted;


    /**
     * 设置：
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置：手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取：手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置：密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取：密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置：
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 获取：
     */
    public String getTenantId() {
        return tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
}
