package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.PositionBiz;
import com.github.wxiaoqi.security.admin.entity.Position;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("position")
public class PositionController extends BaseController<PositionBiz,Position> {

}