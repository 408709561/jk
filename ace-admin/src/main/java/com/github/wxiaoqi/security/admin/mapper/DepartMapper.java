package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.admin.entity.Depart;
import com.github.wxiaoqi.security.admin.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-02-04 19:06:43
 */
public interface DepartMapper extends Mapper<Depart> {

    List<User> selectDepartUsers(String departId);

    void deleteDepartUser(String departId, String userId);

    void insertDepartUser(String id, String departId, String userId);
}
