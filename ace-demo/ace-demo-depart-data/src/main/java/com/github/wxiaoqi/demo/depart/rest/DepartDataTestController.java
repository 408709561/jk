

package com.github.wxiaoqi.demo.depart.rest;

import com.github.wxiaoqi.demo.depart.biz.DepartDataTestBiz;
import com.github.wxiaoqi.demo.depart.entity.DepartDataTest;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("departDataTest")
//@CheckClientToken
@CheckUserToken
public class DepartDataTestController extends BaseController<DepartDataTestBiz,DepartDataTest,String> {

}