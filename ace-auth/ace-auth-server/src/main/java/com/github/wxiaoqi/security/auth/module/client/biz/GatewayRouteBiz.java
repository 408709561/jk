

package com.github.wxiaoqi.security.auth.module.client.biz;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.common.constant.RedisKeyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.auth.module.client.entity.GatewayRoute;
import com.github.wxiaoqi.security.auth.module.client.mapper.GatewayRouteMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;

import java.util.List;

/**
 * @author Mr.AG
 * @version 2018-02-25 12:04:28
 * @email 463540703@qq.com
 */
@Service
public class GatewayRouteBiz extends BusinessBiz<GatewayRouteMapper, GatewayRoute> {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void updateSelectiveById(GatewayRoute entity) {
        super.updateSelectiveById(entity);
        updateGatewayRoute();
    }

    @Override
    public void insertSelective(GatewayRoute entity) {
        super.insertSelective(entity);
        updateGatewayRoute();
    }

    @Override
    public void deleteById(Object id) {
        GatewayRoute gatewayRoute = this.selectById(id);
        gatewayRoute.setEnabled(false);
//        super.deleteById(id);
        this.updateSelectiveById(gatewayRoute);
    }

    @Override
    public void updateById(GatewayRoute entity) {
        super.updateById(entity);
        updateGatewayRoute();
    }

    public void updateGatewayRoute() {
        List<GatewayRoute> gatewayRoutes = this.selectListAll();
        redisTemplate.opsForValue().set(RedisKeyConstants.ZUUL_ROUTE_KEY, JSON.toJSONString(gatewayRoutes));
    }


}