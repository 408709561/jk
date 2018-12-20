

package com.github.wxiaoqi.security.auth.module.client.mapper;

import com.github.wxiaoqi.security.auth.module.client.entity.ClientService;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;

public interface ClientServiceMapper extends CommonMapper<ClientService> {
    void deleteByServiceId(String id);
}
