

package com.github.wxiaoqi.security.auth.module.client.controller;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.auth.module.client.biz.GatewayRouteBiz;
import com.github.wxiaoqi.security.auth.module.client.entity.GatewayRoute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("gatewayRoute")
public class GatewayRouteController extends BaseController<GatewayRouteBiz,GatewayRoute,String> {

}