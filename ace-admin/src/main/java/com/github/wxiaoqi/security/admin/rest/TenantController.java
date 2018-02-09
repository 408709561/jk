package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.admin.biz.TenantBiz;
import com.github.wxiaoqi.security.admin.entity.Tenant;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("tenant")
@CheckClientToken
@CheckUserToken
public class TenantController extends BaseController<TenantBiz,Tenant> {

}