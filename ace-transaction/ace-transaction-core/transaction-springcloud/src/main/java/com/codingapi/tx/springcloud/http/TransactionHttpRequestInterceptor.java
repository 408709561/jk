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

package com.codingapi.tx.springcloud.http;

import com.codingapi.tx.aop.bean.TxTransactionLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created by lorne on 2017/7/3.
 */
public class TransactionHttpRequestInterceptor implements ClientHttpRequestInterceptor {


    private Logger logger = LoggerFactory.getLogger(TransactionHttpRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        TxTransactionLocal txTransactionLocal = TxTransactionLocal.current();
        String groupId = txTransactionLocal == null ? null : txTransactionLocal.getGroupId();
        int maxTimeOut = txTransactionLocal == null ? 0 : txTransactionLocal.getMaxTimeOut();

        logger.info("LCN-SpringCloud TxGroup info -> groupId:"+groupId+",maxTimeOut:"+maxTimeOut);

        if(txTransactionLocal!=null) {
            request.getHeaders().add("tx-group", groupId);
            request.getHeaders().add("tx-maxTimeOut", String.valueOf(maxTimeOut));
        }
        return execution.execute(request,body);
    }
}
