package com.github.wxiaoqi.security.admin.biz;

import com.github.wxiaoqi.security.admin.entity.Tenant;
import com.github.wxiaoqi.security.admin.mapper.TenantMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.stereotype.Service;

/**
 * 租户表
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-02-08 21:42:09
 */
@Service
public class TenantBiz extends BusinessBiz<TenantMapper,Tenant> {
}