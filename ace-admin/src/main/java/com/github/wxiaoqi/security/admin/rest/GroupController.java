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

package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.GroupBiz;
import com.github.wxiaoqi.security.admin.biz.ResourceAuthorityBiz;
import com.github.wxiaoqi.security.admin.constant.AdminCommonConstant;
import com.github.wxiaoqi.security.admin.entity.Group;
import com.github.wxiaoqi.security.admin.vo.AuthorityMenuTree;
import com.github.wxiaoqi.security.admin.vo.GroupTree;
import com.github.wxiaoqi.security.admin.vo.GroupUsers;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-12 8:49
 */
@Controller
@RequestMapping("group")
@Api("群组模块")
@CheckUserToken
@CheckClientToken
public class GroupController extends BaseController<GroupBiz, Group> {
    @Autowired
    private ResourceAuthorityBiz resourceAuthorityBiz;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Group> list(String name,String groupType) {
        if(StringUtils.isBlank(name)&&StringUtils.isBlank(groupType)) {
            return new ArrayList<Group>();
        }
        Example example = new Example(Group.class);
        if (StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(groupType)) {
            example.createCriteria().andEqualTo("groupType", groupType);
        }

        return baseBiz.selectByExample(example);
    }



    @RequestMapping(value = "/{id}/user", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifiyUsers(@PathVariable String id,String members,String leaders){
        baseBiz.modifyGroupUsers(id, members, leaders);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<GroupUsers> getUsers(@PathVariable String id){
        return new ObjectRestResponse<GroupUsers>().data(baseBiz.getGroupUsers(id));
    }

    @RequestMapping(value = "/{id}/authority/menu", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse modifyMenuAuthority(@PathVariable  String id, String menuTrees){
        String [] menus = menuTrees.split(",");
        baseBiz.modifyAuthorityMenu(id, menus);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/{id}/authority/menu", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<AuthorityMenuTree>> getMenuAuthority(@PathVariable  String id){
        return new ObjectRestResponse().data(baseBiz.getAuthorityMenu(id));
    }

    @RequestMapping(value = "/{id}/authority/element/add", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse addElementAuthority(@PathVariable  String id,String menuId, String elementId){
        baseBiz.modifyAuthorityElement(id,menuId,elementId);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/{id}/authority/element/remove", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse removeElementAuthority(@PathVariable String id, String menuId, String elementId){
        baseBiz.removeAuthorityElement(id,menuId,elementId);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/{id}/authority/element", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<Integer>> getElementAuthority(@PathVariable  String id){
        return new ObjectRestResponse().data(baseBiz.getAuthorityElement(id));
    }


    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public List<GroupTree> tree(String name,String groupType) {
//        if(StringUtils.isBlank(name)&&StringUtils.isBlank(groupType)) {
//            return new ArrayList<GroupTree>();
//        }
        Example example = new Example(Group.class);
        if (StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(groupType)) {
            example.createCriteria().andEqualTo("groupType", groupType);
        }
        return  getTree(baseBiz.selectByExample(example), AdminCommonConstant.ROOT);
    }


    private List<GroupTree> getTree(List<Group> groups,String root) {
        List<GroupTree> trees = new ArrayList<GroupTree>();
        GroupTree node = null;
        for (Group group : groups) {
            node = new GroupTree();
            node.setLabel(group.getName());
            BeanUtils.copyProperties(group, node);
            trees.add(node);
        }
        return TreeUtil.bulid(trees,root, null) ;
    }
}
