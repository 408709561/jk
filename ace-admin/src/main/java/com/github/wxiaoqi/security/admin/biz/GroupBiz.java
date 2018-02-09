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

import com.ace.cache.annotation.CacheClear;
import com.github.wxiaoqi.security.admin.constant.AdminCommonConstant;
import com.github.wxiaoqi.security.admin.entity.Group;
import com.github.wxiaoqi.security.admin.entity.Menu;
import com.github.wxiaoqi.security.admin.entity.ResourceAuthority;
import com.github.wxiaoqi.security.admin.mapper.GroupMapper;
import com.github.wxiaoqi.security.admin.mapper.MenuMapper;
import com.github.wxiaoqi.security.admin.mapper.ResourceAuthorityMapper;
import com.github.wxiaoqi.security.admin.mapper.UserMapper;
import com.github.wxiaoqi.security.admin.vo.AuthorityMenuTree;
import com.github.wxiaoqi.security.admin.vo.GroupUsers;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-12 8:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupBiz extends BusinessBiz<GroupMapper, Group> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResourceAuthorityMapper resourceAuthorityMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public void insertSelective(Group entity) {
        if (AdminCommonConstant.ROOT == entity.getParentId()) {
            entity.setPath("/" + entity.getCode());
        } else {
            Group parent = this.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        super.insertSelective(entity);
    }

    @Override
    public void updateById(Group entity) {
        if (AdminCommonConstant.ROOT == entity.getParentId()) {
            entity.setPath("/" + entity.getCode());
        } else {
            Group parent = this.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        super.updateById(entity);
    }

    /**
     * 获取群组关联用户
     *
     * @param groupId
     * @return
     */
    public GroupUsers getGroupUsers(String groupId) {
        return new GroupUsers(userMapper.selectMemberByGroupId(groupId), userMapper.selectLeaderByGroupId(groupId));
    }

    /**
     * 变更群主所分配用户
     *
     * @param groupId
     * @param members
     * @param leaders
     */
    @CacheClear(pre = "permission")
    public void modifyGroupUsers(String groupId, String members, String leaders) {
        mapper.deleteGroupLeadersById(groupId);
        mapper.deleteGroupMembersById(groupId);
        if (!StringUtils.isEmpty(members)) {
            String[] mem = members.split(",");
            for (String m : mem) {
                mapper.insertGroupMembersById(UUIDUtils.generateUuid(), groupId, m);
            }
        }
        if (!StringUtils.isEmpty(leaders)) {
            String[] mem = leaders.split(",");
            for (String m : mem) {
                mapper.insertGroupLeadersById(UUIDUtils.generateUuid(), groupId, m);
            }
        }
    }

    /**
     * 变更群组关联的菜单
     *
     * @param groupId
     * @param menus
     */
    @CacheClear(keys = {"permission:menu", "permission:u"})
    public void modifyAuthorityMenu(String groupId, String[] menus) {
        resourceAuthorityMapper.deleteByAuthorityIdAndResourceType(groupId + "", AdminCommonConstant.RESOURCE_TYPE_MENU);
        List<Menu> menuList = menuMapper.selectAll();
        Map<String, String> map = new HashMap<String, String>();
        for (Menu menu : menuList) {
            map.put(menu.getId().toString(), menu.getParentId().toString());
        }
        Set<String> relationMenus = new HashSet<String>();
        relationMenus.addAll(Arrays.asList(menus));
        ResourceAuthority authority = null;
        for (String menuId : menus) {
            findParentID(map, relationMenus, menuId);
        }
        for (String menuId : relationMenus) {
            authority = new ResourceAuthority(AdminCommonConstant.AUTHORITY_TYPE_GROUP, AdminCommonConstant.RESOURCE_TYPE_MENU);
            authority.setAuthorityId(groupId + "");
            authority.setResourceId(menuId);
            authority.setParentId("-1");
            resourceAuthorityMapper.insertSelective(authority);
        }
    }

    private void findParentID(Map<String, String> map, Set<String> relationMenus, String id) {
        String parentId = map.get(id);
        if (String.valueOf(AdminCommonConstant.ROOT).equals(id) || parentId == null) {
            return;
        }
        relationMenus.add(parentId);
        findParentID(map, relationMenus, parentId);
    }

    /**SimpleRouteLocator
     * 分配资源权限
     *
     * @param groupId
     * @param menuId
     * @param elementId
     */
    @CacheClear(keys = {"permission:ele", "permission:u"})
    public void modifyAuthorityElement(String groupId, String menuId, String elementId) {
        ResourceAuthority authority = new ResourceAuthority(AdminCommonConstant.AUTHORITY_TYPE_GROUP, AdminCommonConstant.RESOURCE_TYPE_BTN);
        authority.setAuthorityId(groupId + "");
        authority.setResourceId(elementId + "");
        authority.setParentId("-1");
        resourceAuthorityMapper.insertSelective(authority);
    }

    /**
     * 移除资源权限
     *
     * @param groupId
     * @param menuId
     * @param elementId
     */
    @CacheClear(keys = {"permission:ele", "permission:u"})
    public void removeAuthorityElement(String groupId, String menuId, String elementId) {
        ResourceAuthority authority = new ResourceAuthority();
        authority.setAuthorityId(groupId + "");
        authority.setResourceId(elementId + "");
        authority.setParentId("-1");
        resourceAuthorityMapper.delete(authority);
    }


    /**
     * 获取群主关联的菜单
     *
     * @param groupId
     * @return
     */
    public List<AuthorityMenuTree> getAuthorityMenu(String groupId) {
        List<Menu> menus = menuMapper.selectMenuByAuthorityId(String.valueOf(groupId), AdminCommonConstant.AUTHORITY_TYPE_GROUP);
        List<AuthorityMenuTree> trees = new ArrayList<AuthorityMenuTree>();
        AuthorityMenuTree node = null;
        for (Menu menu : menus) {
            node = new AuthorityMenuTree();
            node.setText(menu.getTitle());
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        return trees;
    }

    /**
     * 获取群组关联的资源
     *
     * @param groupId
     * @return
     */
    public List<Integer> getAuthorityElement(String groupId) {
        ResourceAuthority authority = new ResourceAuthority(AdminCommonConstant.AUTHORITY_TYPE_GROUP, AdminCommonConstant.RESOURCE_TYPE_BTN);
        authority.setAuthorityId(groupId + "");
        List<ResourceAuthority> authorities = resourceAuthorityMapper.select(authority);
        List<Integer> ids = new ArrayList<Integer>();
        for (ResourceAuthority auth : authorities) {
            ids.add(Integer.parseInt(auth.getResourceId()));
        }
        return ids;
    }
}
