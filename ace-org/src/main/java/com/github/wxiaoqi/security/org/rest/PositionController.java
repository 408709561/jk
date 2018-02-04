package com.github.wxiaoqi.security.org.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.org.biz.PositionBiz;
import com.github.wxiaoqi.security.org.entity.Position;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("position")
public class PositionController extends BaseController<PositionBiz,Position> {

}