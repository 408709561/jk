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

package com.codingapi.tx.netty.service.impl;

import com.codingapi.tx.Constants;
import com.codingapi.tx.model.TxServer;
import com.codingapi.tx.netty.service.MQTxManagerService;
import com.codingapi.tx.netty.service.NettyDistributeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lorne on 2017/6/30.
 */
@Service
public class NettyDistributeServiceImpl implements NettyDistributeService {

    private int connectCont = 0;

    private Logger logger = LoggerFactory.getLogger(NettyDistributeServiceImpl.class);

    @Autowired
    private MQTxManagerService txManagerService;

    @Override
    public synchronized void loadTxServer() {
        if (Constants.txServer == null) {
            getTxServer();
            return;
        }
        connectCont++;
        if (connectCont == 3) {
            getTxServer();
        }
    }

    private void getTxServer() {
        //获取负载均衡服务地址
        String json = null;
        while (StringUtils.isEmpty(json)) {
            json = txManagerService.httpGetServer();
            logger.info("获取manager服务信息->" + json);
            if (StringUtils.isEmpty(json)) {
                logger.info("TxManager服务器无法访问.");
                try {
                    Thread.sleep(1000 * 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        TxServer txServer = TxServer.parser(json);
        if (txServer != null) {
            logger.info("txServer -> " + txServer);
            logger.info(txServer.toString());
            Constants.txServer = txServer;
            logger.info(Constants.txServer.toString());
            connectCont = 0;
        }

    }

}
