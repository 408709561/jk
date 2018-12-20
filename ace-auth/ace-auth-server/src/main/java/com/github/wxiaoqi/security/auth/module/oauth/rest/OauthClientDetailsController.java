

package com.github.wxiaoqi.security.auth.module.oauth.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.auth.module.oauth.biz.OauthClientDetailsBiz;
import com.github.wxiaoqi.security.auth.module.oauth.entity.OauthClientDetails;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("oauthClientDetails")
public class OauthClientDetailsController extends BaseController<OauthClientDetailsBiz,OauthClientDetails,String> {

}