

package com.github.wxiaoqi.demo.depart.mapper;

import com.github.wxiaoqi.demo.depart.entity.DepartDataTest;
import com.github.wxiaoqi.security.common.data.Depart;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.common.data.Tenant;

/**
 * 
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-02-11 16:05:19
 */
@Depart
@Tenant
public interface DepartDataTestMapper extends CommonMapper<DepartDataTest> {
	
}
