/*
 *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>

 *  AG-Enterprise 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  老A将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.github.wxiaoqi.security.admin.biz;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.merge.core.MergeCore;
import com.github.wxiaoqi.security.admin.constant.UserConstant;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.mapper.DepartMapper;
import com.github.wxiaoqi.security.admin.mapper.UserMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.BooleanUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-08 16:23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserBiz extends BusinessBiz<UserMapper, User> {
    @Autowired
    private MergeCore mergeCore;

    @Autowired
    private DepartMapper departMapper;

    @Override
    public User selectById(Object id) {
        User user = super.selectById(id);
        try {
            mergeCore.mergeOne(User.class, user);
            return user;
        } catch (Exception e) {
            return super.selectById(id);
        }
    }

    @Override
    public void insertSelective(User entity) {
        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(entity.getPassword());
        entity.setPassword(password);
        entity.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        entity.setIsDisabled(BooleanUtil.BOOLEAN_FALSE);
        String userId = UUIDUtils.generateUuid();
        entity.setTenantId(BaseContextHandler.getTenantID());
        entity.setId(userId);
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            entity.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        }
        departMapper.insertDepartUser(UUIDUtils.generateUuid(), entity.getDepartId(), entity.getId(),BaseContextHandler.getTenantID());
        super.insertSelective(entity);
    }

    @Override
    @CacheClear(pre = "user{1.username}")
    public void updateSelectiveById(User entity) {
        User user = mapper.selectByPrimaryKey(entity.getId());
        if (!user.getDepartId().equals(entity.getDepartId())) {
            departMapper.deleteDepartUser(user.getDepartId(), entity.getId());
            departMapper.insertDepartUser(UUIDUtils.generateUuid(), entity.getDepartId(), entity.getId(),BaseContextHandler.getTenantID());
        }
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            entity.setTenantId(BaseContextHandler.getTenantID());
        }
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            entity.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        }
        super.updateSelectiveById(entity);
    }

    @Override
    public void deleteById(Object id) {
        User user = mapper.selectByPrimaryKey(id);
        user.setIsDeleted(BooleanUtil.BOOLEAN_TRUE);
        this.updateSelectiveById(user);
    }

    @Override
    public List<User> selectByExample(Object obj) {
        Example example = (Example) obj;
        example.createCriteria().andEqualTo("isDeleted", BooleanUtil.BOOLEAN_FALSE);
        List<User> users = super.selectByExample(example);
        try {
            mergeCore.mergeResult(User.class, users);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    @Cache(key = "user{1}")
    public User getUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        user.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        user.setIsDisabled(BooleanUtil.BOOLEAN_FALSE);
        return mapper.selectOne(user);
    }


    public List<String> getUserDataDepartIds(String userId) {
        return mapper.selectUserDataDepartIds(userId);
    }
}
