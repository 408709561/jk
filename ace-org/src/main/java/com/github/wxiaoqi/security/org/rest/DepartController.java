package com.github.wxiaoqi.security.org.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.github.wxiaoqi.security.org.biz.DepartBiz;
import com.github.wxiaoqi.security.org.entity.Depart;
import com.github.wxiaoqi.security.org.vo.DepartTree;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("depart")
@CheckClientToken
@CheckUserToken
public class DepartController extends BaseController<DepartBiz,Depart> {
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List<DepartTree> getTree() {
        List<Depart> dictTypes = this.baseBiz.selectListAll();
        List<DepartTree> trees = new ArrayList<>();
        dictTypes.forEach(dictType -> {
            trees.add(new DepartTree(dictType.getId(), dictType.getParentId(), dictType.getName(),dictType.getCode()));
        });
        return TreeUtil.bulid(trees, "-1", null);
    }
}