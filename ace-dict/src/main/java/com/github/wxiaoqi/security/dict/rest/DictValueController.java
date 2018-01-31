package com.github.wxiaoqi.security.dict.rest;

import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.dict.biz.DictValueBiz;
import com.github.wxiaoqi.security.dict.entity.DictValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dictValue")
public class DictValueController extends BaseController<DictValueBiz,DictValue> {

}