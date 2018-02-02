package com.github.wxiaoqi.security.dict.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.github.wxiaoqi.security.dict.biz.DictTypeBiz;
import com.github.wxiaoqi.security.dict.entity.DictType;
import com.github.wxiaoqi.security.dict.vo.DictTree;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("dictType")
@CheckClientToken
@CheckUserToken
public class DictTypeController extends BaseController<DictTypeBiz, DictType> {
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List<DictTree> getTree() {
        List<DictType> dictTypes = this.baseBiz.selectListAll();
        List<DictTree> trees = new ArrayList<>();
        dictTypes.forEach(dictType -> {
            trees.add(new DictTree(dictType.getId(), dictType.getParentId(), dictType.getName(),dictType.getCode()));
        });
        return TreeUtil.bulid(trees, "-1", null);
    }
}