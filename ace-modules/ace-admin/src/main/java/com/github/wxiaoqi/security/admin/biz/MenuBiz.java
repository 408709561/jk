

package com.github.wxiaoqi.security.admin.biz;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.github.wxiaoqi.security.admin.constant.AdminCommonConstant;
import com.github.wxiaoqi.security.admin.entity.Menu;
import com.github.wxiaoqi.security.admin.mapper.MenuMapper;
import com.github.wxiaoqi.security.admin.mapper.UserMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.BooleanUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserMapper userMapper;

    @Override
    @Cache(key = "permission:menu")
    public List<Menu> selectListAll() {
        return super.selectListAll();
    }

    @Override
    @CacheClear(keys = {"permission:menu", "permission"})
    public void insertSelective(Menu entity) {
        if (AdminCommonConstant.ROOT.equals(entity.getParentId())) {
            entity.setPath("/" + entity.getCode());
        } else {
            Menu parent = this.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        super.insertSelective(entity);
    }

    @Override
    @CacheClear(keys = {"permission:menu", "permission"})
    public void deleteById(Object id) {
        super.deleteById(id);
    }

    @Override
    @CacheClear(keys = {"permission:menu", "permission"})
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
    @CacheClear(keys = {"permission:menu", "permission"})
    public void updateSelectiveById(Menu entity) {
        super.updateSelectiveById(entity);
    }

    /**
     * 获取用户可以访问的菜单
     *
     * @param userId
     * @return
     */
    @Cache(key = "permission:menu:u{1}")
    public List<Menu> getUserAuthorityMenuByUserId(String userId) {
        if (BooleanUtil.BOOLEAN_TRUE.equals(userMapper.selectByPrimaryKey(userId).getIsSuperAdmin())) {
            return this.selectListAll();
        }
        return mapper.selectAuthorityMenuByUserId(userId, AdminCommonConstant.RESOURCE_TYPE_VIEW);
    }

    /**
     * 根据用户获取可以访问的系统
     *
     * @param id
     * @return
     */
    public List<Menu> getUserAuthoritySystemByUserId(String id) {
        return mapper.selectAuthoritySystemByUserId(id, AdminCommonConstant.RESOURCE_TYPE_VIEW);
    }
}
