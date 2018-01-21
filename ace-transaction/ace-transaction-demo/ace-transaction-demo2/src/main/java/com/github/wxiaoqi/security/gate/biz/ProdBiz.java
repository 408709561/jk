package com.github.wxiaoqi.security.gate.biz;

import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.gate.entity.Prod;
import com.github.wxiaoqi.security.gate.mapper.ProdMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-01-18 11:11:06
 */
@Service
public class ProdBiz extends BaseBiz<ProdMapper,Prod> {
    @Transactional
    public void test(){
        Prod prod = mapper.selectByPrimaryKey(1);
        prod.setNum(prod.getNum()-2);
        mapper.updateByPrimaryKey(prod);
    }
}