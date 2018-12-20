

package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.MenuBiz;
import com.github.wxiaoqi.security.admin.biz.UserBiz;
import com.github.wxiaoqi.security.admin.constant.AdminCommonConstant;
import com.github.wxiaoqi.security.admin.entity.Menu;
import com.github.wxiaoqi.security.admin.vo.MenuTree;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-12 8:49
 */
@Controller
@RequestMapping("menu")
@CheckUserToken
@CheckClientToken
@Api(tags="菜单模块")
public class MenuController extends BaseController<MenuBiz, Menu,String> {
    @Autowired
    private UserBiz userBiz;

    @ApiOperation("获取菜单列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Menu> list(String title) {
        Example example = new Example(Menu.class);
        if (StringUtils.isNotBlank(title)) {
            example.createCriteria().andLike("title", "%" + title + "%");
        }
        return baseBiz.selectByExample(example);
    }

    @ApiOperation("获取菜单树")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuTree> getTree(String title) {
        Example example = new Example(Menu.class);
        if (StringUtils.isNotBlank(title)) {
            example.createCriteria().andLike("title", "%" + title + "%");
        }
        return MenuTree.buildTree(baseBiz.selectByExample(example), AdminCommonConstant.ROOT);
    }

}
