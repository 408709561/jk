package com.github.wxiaoqi.security.auth.module.client.mapper;

import com.github.wxiaoqi.security.auth.module.client.entity.ClientService;
import tk.mybatis.mapper.common.Mapper;

public interface ClientServiceMapper extends Mapper<ClientService> {
    void deleteByServiceId(int id);
}