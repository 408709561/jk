

package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.TenantBiz;
import com.github.wxiaoqi.security.admin.biz.UserBiz;
import com.github.wxiaoqi.security.admin.entity.Tenant;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租户管理
 * @author ace
 */
@RestController
@RequestMapping("tenant")
@CheckClientToken
@CheckUserToken
@Api(tags = "租户模块")
public class TenantController extends BaseController<TenantBiz,Tenant,String> {
    @Autowired
    private UserBiz userBiz;
    @ApiOperation("租户授予用户")
    @RequestMapping(value = "/{id}/user",method = RequestMethod.PUT)
    public ObjectRestResponse<Boolean> updateUser(@PathVariable("id") String id, String userId){
        baseBiz.updateUser(id,userId);
        return new ObjectRestResponse<>();
    }

    @ApiOperation("获取租户授予用户")
    @RequestMapping(value = "/{id}/user",method = RequestMethod.GET)
    public ObjectRestResponse<User> updateUser(@PathVariable("id") String id){
        Tenant tenant = baseBiz.selectById(id);
        return new ObjectRestResponse<>().data(userBiz.selectById(tenant.getOwner()));
    }
}