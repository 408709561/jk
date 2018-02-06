package com.github.wxiaoqi.security.admin.biz;

import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.github.wxiaoqi.security.admin.entity.Depart;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.mapper.DepartMapper;
import com.github.wxiaoqi.security.admin.service.TableResultParser;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-02-04 19:06:43
 */
@Service
public class DepartBiz extends BusinessBiz<DepartMapper,Depart> {
    @MergeResult(resultParser = TableResultParser.class)
    public TableResultResponse<User> getDepartUsers(String departId,String userName) {
        List<User> users = this.mapper.selectDepartUsers(departId,userName);
        return new TableResultResponse<User>(users.size(),users);
    }

    public void addDepartUser(String departId, String userIds) {
        if (!StringUtils.isEmpty(userIds)) {
            String[] uIds = userIds.split(",");
            for (String uId : uIds) {
                this.mapper.insertDepartUser(UUIDUtils.generateUuid(),departId,uId);
            }
        }
    }

    public void delDepartUser(String departId, String userId) {
        this.mapper.deleteDepartUser(departId,userId);
    }
}