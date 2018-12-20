package com.bijiemaifang.estate.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.bijiemaifang.estate.biz.EstateBiz;
import com.bijiemaifang.estate.entity.Estate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;

@RestController
@RequestMapping("estatemanager")
@CheckClientToken
@CheckUserToken
public class EstateController extends BaseController<EstateBiz,Estate,Integer> {

}