

package com.github.wxiaoqi.security.admin.service;

import com.github.wxiaoqi.security.admin.biz.UserBiz;
import com.github.wxiaoqi.security.common.data.IUserDepartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ace
 * @create 2018/2/11.
 */
@Component
public class UserDepartDataService implements IUserDepartDataService {
    @Autowired
    private UserBiz userFeign;
    @Override
    public List<String> getUserDataDepartIds(String userId) {
        // 获取用户授权的部门数据权限,此处模拟两个账户
        return userFeign.getUserDataDepartIds(userId);
    }
}
