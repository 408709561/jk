

package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.admin.entity.GateLog;
import com.github.wxiaoqi.security.common.data.Tenant;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

@Tenant(userField = "crt_user")
public interface GateLogMapper extends CommonMapper<GateLog> {
}
