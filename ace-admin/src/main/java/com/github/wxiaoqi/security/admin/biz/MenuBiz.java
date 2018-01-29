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
import com.github.wxiaoqi.security.admin.constant.AdminCommonConstant;
import com.github.wxiaoqi.security.admin.entity.Menu;
import com.github.wxiaoqi.security.admin.mapper.MenuMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-12 8:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuBiz extends BusinessBiz<MenuMapper, Menu> {
    @Override
    @Cache(key="permission:menu")
    public List<Menu> selectListAll() {
        return super.selectListAll();
    }

    @Override
    @CacheClear(keys={"permission:menu","permission"})
    public void insertSelective(Menu entity) {
        if (AdminCommonConstant.ROOT == entity.getParentId()) {
            entity.setPath("/" + entity.getCode());
        } else {
            Menu parent = this.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        super.insertSelective(entity);
    }

    @Override
    @CacheClear(keys={"permission:menu","permission"})
    public void updateById(Menu entity) {
        if (AdminCommonConstant.ROOT == entity.getParentId()) {
            entity.setPath("/" + entity.getCode());
        } else {
            Menu parent = this.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        super.updateById(entity);
    }

    @Override
    @CacheClear(keys={"permission:menu","permission"})
    public void updateSelectiveById(Menu entity) {
        super.updateSelectiveById(entity);
    }

    /**
     * 获取用户可以访问的菜单
     *
     * @param id
     * @return
     */
    @Cache(key = "permission:menu:u{1}")
    public List<Menu> getUserAuthorityMenuByUserId(int id) {
        return mapper.selectAuthorityMenuByUserId(id);
    }

    /**
     * 根据用户获取可以访问的系统
     *
     * @param id
     * @return
     */
    public List<Menu> getUserAuthoritySystemByUserId(int id) {
        return mapper.selectAuthoritySystemByUserId(id);
    }
}
