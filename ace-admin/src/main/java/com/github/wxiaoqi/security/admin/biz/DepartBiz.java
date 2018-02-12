package com.github.wxiaoqi.security.admin.biz;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.github.wxiaoqi.security.admin.entity.Depart;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.mapper.DepartMapper;
import com.github.wxiaoqi.security.admin.mapper.UserMapper;
import com.github.wxiaoqi.security.admin.service.TableResultParser;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-02-04 19:06:43
 */
@Service
public class DepartBiz extends BusinessBiz<DepartMapper,Depart> {
    @Autowired
    private UserMapper userMapper;
    @MergeResult(resultParser = TableResultParser.class)
    public TableResultResponse<User> getDepartUsers(String departId,String userName) {
        List<User> users = this.mapper.selectDepartUsers(departId,userName);
        return new TableResultResponse<User>(users.size(),users);
    }

    public void addDepartUser(String departId, String userIds) {
        if (!StringUtils.isEmpty(userIds)) {
            String[] uIds = userIds.split(",");
            for (String uId : uIds) {
                this.mapper.insertDepartUser(UUIDUtils.generateUuid(),departId,uId, BaseContextHandler.getTenantID());
            }
        }
    }

    /**
     * 根据ID批量获取部门值
     * @param departIDs
     * @return
     */
    public Map<String,String> getDeparts(String departIDs){
        if(StringUtils.isBlank(departIDs)) {
            return new HashMap<>();
        }
        departIDs = "'"+departIDs.replaceAll(",","','")+"'";
        List<Depart> departs = mapper.selectByIds(departIDs);
        return departs.stream().collect(Collectors.toMap(Depart::getId, depart -> JSONObject.toJSONString(depart)));
    }

    public void delDepartUser(String departId, String userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user.getDepartId().equals(departId)){
            throw new BusinessException("无法移除用户的默认关联部门,若需移除,请前往用户模块更新用户部门!");
        }
        this.mapper.deleteDepartUser(departId,userId);
    }
}