

package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.common.data.Tenant;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Tenant()
public interface UserMapper extends CommonMapper<User> {
    public List<User> selectMemberByGroupId(@Param("groupId") String groupId);
    public List<User> selectLeaderByGroupId(@Param("groupId") String groupId);
    List<String> selectUserDataDepartIds(String userId);
}
