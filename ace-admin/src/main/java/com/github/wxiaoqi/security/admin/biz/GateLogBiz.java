package com.github.wxiaoqi.security.admin.biz;

import com.github.wxiaoqi.security.admin.entity.GateLog;
import com.github.wxiaoqi.security.admin.mapper.GateLogMapper;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-07-01 14:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GateLogBiz extends BaseBiz<GateLogMapper,GateLog> {

    @Override
    public void insertSelective(GateLog entity) {
        mapper.insertSelective(entity);
    }
}
