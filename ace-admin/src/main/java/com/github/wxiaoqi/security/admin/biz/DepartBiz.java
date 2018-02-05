package com.github.wxiaoqi.security.admin.biz;

import com.github.wxiaoqi.security.admin.entity.Depart;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.mapper.DepartMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sun.tools.javac.util.List;
import org.springframework.stereotype.Service;

/**
 * 
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-02-04 19:06:43
 */
@Service
public class DepartBiz extends BusinessBiz<DepartMapper,Depart> {
    public TableResultResponse<User> getDepartUsers(String departId) {
        List<User> users = this.mapper.selectDepartUsers(departId);
        return new TableResultResponse<User>(users.size(),users);
    }

    public void addDepartUser(String departId, String userId) {
        this.mapper.insertDepartUser(UUIDUtils.generateUuid(),departId,userId);
    }

    public void delDepartUser(String departId, String userId) {
        this.mapper.deleteDepartUser(departId,userId);
    }
}