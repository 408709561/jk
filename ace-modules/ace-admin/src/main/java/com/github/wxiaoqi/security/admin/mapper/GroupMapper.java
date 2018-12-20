

package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.admin.entity.Group;
import com.github.wxiaoqi.security.common.data.Tenant;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;
@Tenant()
public interface GroupMapper extends CommonMapper<Group> {
    public void deleteGroupMembersById (@Param("groupId") String groupId);
    public void deleteGroupLeadersById (@Param("groupId") String groupId);
    public void insertGroupMembersById (@Param("id") String id,@Param("groupId") String groupId,@Param("userId") String userId,@Param("tenantId") String tenantId);
    public void insertGroupLeadersById (@Param("id") String id,@Param("groupId") String groupId,@Param("userId") String userId,@Param("tenantId") String tenantId);
}
