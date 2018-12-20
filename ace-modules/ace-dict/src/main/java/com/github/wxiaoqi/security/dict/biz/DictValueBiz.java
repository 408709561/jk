


package com.github.wxiaoqi.security.dict.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.dict.entity.DictValue;
import com.github.wxiaoqi.security.dict.mapper.DictValueMapper;
import org.springframework.stereotype.Service;

/**
 * 
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-01-30 19:45:55
 */
@Service
public class DictValueBiz extends BusinessBiz<DictValueMapper,DictValue> {
    @Override
    public void insertSelective(DictValue entity) {
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
    }
}