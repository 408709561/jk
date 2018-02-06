package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.DepartBiz;
import com.github.wxiaoqi.security.admin.entity.Depart;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.vo.DepartTree;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.TreeUtil;
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

    @RequestMapping(value = "user",method = RequestMethod.GET)
    public TableResultResponse<User> getDepartUsers(String departId,String userName){
        return this.baseBiz.getDepartUsers(departId,userName);
    }

    @RequestMapping(value = "user",method = RequestMethod.POST)
    public ObjectRestResponse<Boolean> addDepartUser(String departId, String userIds){
        this.baseBiz.addDepartUser(departId,userIds);
        return new ObjectRestResponse<>().data(true);
    }

    @RequestMapping(value = "user",method = RequestMethod.DELETE)
    public ObjectRestResponse<Boolean> delDepartUser(String departId,String userId){
        this.baseBiz.delDepartUser(departId,userId);
        return new ObjectRestResponse<>().data(true);
    }

}