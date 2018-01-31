package com.github.wxiaoqi.security.dict.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.dict.entity.DictType;
import com.github.wxiaoqi.security.dict.mapper.DictTypeMapper;
import org.springframework.stereotype.Service;

/**
 * 
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-01-30 19:45:55
 */
@Service
public class DictTypeBiz extends BusinessBiz<DictTypeMapper,DictType> {
    @Override
    public void insertSelective(DictType entity) {
        entity.setId(UUIDUtils.generateUuid());
        super.insertSelective(entity);
    }
}