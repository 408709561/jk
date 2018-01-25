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

package com.codingapi.tm.compensate.dao.impl;

import com.alibaba.fastjson.JSON;
import com.codingapi.tm.compensate.dao.CompensateDao;
import com.codingapi.tm.compensate.model.TransactionCompensateMsg;
import com.codingapi.tm.config.ConfigReader;
import com.codingapi.tm.redis.service.RedisServerService;
import com.lorne.core.framework.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * create by lorne on 2017/11/11
 */
@Service
public class CompensateDaoImpl implements CompensateDao {


    @Autowired
    private RedisServerService redisServerService;

    @Autowired
    private ConfigReader configReader;


    @Override
    public String saveCompensateMsg(TransactionCompensateMsg transactionCompensateMsg) {

        String name = String.format("%s%s_%s_%s.json", configReader.getKeyPrefixCompensate(), transactionCompensateMsg.getModel(), DateUtil.getCurrentDateFormat(), transactionCompensateMsg.getGroupId());

        String json = JSON.toJSONString(transactionCompensateMsg);

        redisServerService.saveCompensateMsg(name, json);

        return name;
    }


    @Override
    public List<String> loadCompensateKeys() {
        String key = configReader.getKeyPrefixCompensate() + "*";
        return redisServerService.getKeys(key);
    }


    @Override
    public boolean hasCompensate() {
        String key = configReader.getKeyPrefixCompensate() + "*";
        List<String> keys = redisServerService.getKeys(key);
        return keys != null && keys.size() > 0;
    }

    @Override
    public List<String> loadCompensateTimes(String model) {
        String key = configReader.getKeyPrefixCompensate() + model + "_*";
        List<String> keys = redisServerService.getKeys(key);
        List<String> times = new ArrayList<>();
        for (String k : keys) {
            if(k.length()>36) {
                String time = k.substring(k.length() - 24, k.length() - 14);
                if (!times.contains(time)) {
                    times.add(time);
                }
            }
        }
        return times;
    }


    @Override
    public List<String> loadCompensateByModelAndTime(String path) {
        String key = String.format("%s%s*", configReader.getKeyPrefixCompensate(), path);
        List<String> keys = redisServerService.getKeys(key);
        List<String> values = redisServerService.getValuesByKeys(keys);
        return values;
    }

    @Override
    public String getCompensate(String path) {
        String key = String.format("%s%s.json", configReader.getKeyPrefixCompensate(), path);
        return redisServerService.getValueByKey(key);
    }


    @Override
    public void deleteCompensateByPath(String path) {
        String key = String.format("%s%s.json", configReader.getKeyPrefixCompensate(), path);
        redisServerService.deleteKey(key);
    }


    @Override
    public void deleteCompensateByKey(String key) {
        redisServerService.deleteKey(key);
    }

    @Override
    public String getCompensateByGroupId(String groupId) {
        String key = String.format("%s*%s.json", configReader.getKeyPrefixCompensate(), groupId);
        List<String> keys = redisServerService.getKeys(key);
        if (keys != null && keys.size() == 1) {
            String k = keys.get(0);
            return redisServerService.getValueByKey(k);
        }
        return null;
    }
}
