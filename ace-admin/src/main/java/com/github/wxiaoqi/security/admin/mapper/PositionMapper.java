package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.admin.entity.Group;
import com.github.wxiaoqi.security.admin.entity.Position;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-02-04 19:06:43
 */
public interface PositionMapper extends CommonMapper<Position> {
    /**
     * 批量删除岗位中得用户
     * @param positionId
     */
    void deletePositionUsers(String positionId);

    /**
     * 岗位增加用户
     * @param id
     * @param positionId
     * @param userId
     */
    void insertPositionUser(@Param("id")String id, @Param("positionId")String positionId, @Param("userId") String userId);

    /**
     * 获取岗位关联的用户
     * @param positionId
     * @return
     */
    List<User> selectPositionUsers(String positionId);

    /**
     * 删除岗位关联的角色
     * @param positionId
     */
    void deletePositionGroups(String positionId);

    /**
     * 插入岗位关联的角色
     * @param id
     * @param positionId
     * @param groupId
     */
    void insertPositionGroup(@Param("id")String id, @Param("positionId")String positionId, @Param("groupId") String groupId);

    /**
     * 获取岗位关联的角色
     * @param positionId
     * @return
     */
    List<Group> selectPositionGroups( @Param("positionId")String positionId);
}
