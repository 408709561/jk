package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.admin.entity.Depart;
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
public interface DepartMapper extends CommonMapper<Depart> {

    List<User> selectDepartUsers(@Param("departId") String departId,@Param("userName") String userName);

    void deleteDepartUser(@Param("departId")String departId, @Param("userId") String userId);

    void insertDepartUser(@Param("id") String id, @Param("departId") String departId, @Param("userId") String userId,@Param("tenantId") String tenantId);

}
