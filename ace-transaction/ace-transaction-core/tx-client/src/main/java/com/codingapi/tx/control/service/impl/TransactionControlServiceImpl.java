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

package com.codingapi.tx.control.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.codingapi.tx.control.service.IActionService;
import com.codingapi.tx.control.service.TransactionControlService;
import com.codingapi.tx.framework.utils.SocketUtils;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * create by lorne on 2017/11/11
 */
@Service
public class TransactionControlServiceImpl implements TransactionControlService{

    private Logger logger = LoggerFactory.getLogger(TransactionControlServiceImpl.class);


    @Autowired
    private ApplicationContext spring;



    @Override
    public void notifyTransactionMsg(ChannelHandlerContext ctx,JSONObject resObj, String json) {


        String action = resObj.getString("a");
        String key = resObj.getString("k");

        IActionService actionService = spring.getBean(action, IActionService.class);

        String res = actionService.execute(resObj, json);


        JSONObject data = new JSONObject();
        data.put("k", key);
        data.put("a", action);

        JSONObject params = new JSONObject();
        params.put("d", res);
        data.put("p", params);

        SocketUtils.sendMsg(ctx, data.toString());

        logger.info("send notify data ->" + data.toString());
    }
}
