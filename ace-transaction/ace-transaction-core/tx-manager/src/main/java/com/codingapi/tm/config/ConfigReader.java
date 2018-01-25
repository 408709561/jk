/*
 *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>

 *  AG-Enterprise 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  老A将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.codingapi.tm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * create by lorne on 2017/11/11
 */
@Component
public class ConfigReader {

    @Value("${tm.socket.port}")
    private int socketPort;

    @Value("${tm.socket.maxconnection}")
    private int socketMaxConnection;

    @Value("${tm.transaction.netty.hearttime}")
    private int transactionNettyHeartTime;

    @Value("${tm.transaction.netty.delaytime}")
    private int transactionNettyDelayTime;

    @Value("${tm.redis.savemaxtime}")
    private int redisSaveMaxTime;


    @Value("${tm.compensate.notifyUrl}")
    private String compensateNotifyUrl;


    @Value("${tm.compensate.auto}")
    private boolean isCompensateAuto;


    @Value("${tm.compensate.tryTime}")
    private int compensateTryTime;
    
    @Value("${tm.auto.compensate.limit}")
    private int autoCompensateLimit;


    /**
     * 事务默认数据的位置，有最大时间
     */
    private final String key_prefix = "tx_manager_default_";

    /**
     * 负载均衡模块存储信息
     */
    private final String key_prefix_loadbalance = "loadbalance_";


    /**
     * 补偿事务永久存储数据
     */
    private final String key_prefix_compensate = "compensate_";


    public String getKeyPrefixLoadbalance() {
        return key_prefix_loadbalance;
    }

    public String getCompensateNotifyUrl() {
        return compensateNotifyUrl;
    }

    public String getKeyPrefix() {
        return key_prefix;
    }

    public String getKeyPrefixCompensate() {
        return key_prefix_compensate;
    }

    public int getSocketPort(){
        return socketPort;
    }

    public int getSocketMaxConnection() {
        return socketMaxConnection;
    }

    public int getTransactionNettyHeartTime() {
        return transactionNettyHeartTime;
    }

    public int getRedisSaveMaxTime() {
        return redisSaveMaxTime;
    }

    public int getTransactionNettyDelayTime() {
        return transactionNettyDelayTime;
    }

    public boolean isCompensateAuto() {
        return isCompensateAuto;
    }

    public int getCompensateTryTime() {
        return compensateTryTime;
    }

	public int getAutoCompensateLimit() {
		return autoCompensateLimit;
	}
    
    
}
