package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.admin.entity.Position;
import com.github.wxiaoqi.security.admin.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-02-04 19:06:43
 */
public interface PositionMapper extends Mapper<Position> {
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

    List<User> selectPositionUsers(String positionId);
}
