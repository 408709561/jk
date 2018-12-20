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

package com.codingapi.tx.aop.service.impl;


import com.codingapi.tx.aop.bean.TxTransactionInfo;
import com.codingapi.tx.aop.service.TransactionServer;
import com.codingapi.tx.aop.service.TransactionServerFactoryService;
import com.codingapi.tx.datasource.ILCNTransactionControl;
import com.codingapi.tx.netty.service.NettyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Created by lorne on 2017/6/8.
 */
@Service
public class TransactionServerFactoryServiceImpl implements TransactionServerFactoryService {


    @Autowired
    private TransactionServer txStartTransactionServer;

    @Autowired
    private TransactionServer txRunningTransactionServer;

    @Autowired
    private TransactionServer txDefaultTransactionServer;

    @Autowired
    private TransactionServer txRunningNoTransactionServer;

    @Autowired
    private NettyService nettyService;

    @Autowired
    private ILCNTransactionControl transactionControl;


    public TransactionServer createTransactionServer(TxTransactionInfo info) throws Throwable {


        /*********分布式事务处理逻辑*开始***********/

        /** 尽当Transaction注解不为空，其他都为空时。表示分布式事务开始启动 **/
        if (info.getTransaction() != null && info.getTxTransactionLocal() == null && StringUtils.isEmpty(info.getTxGroupId())) {
            //检查socket通讯是否正常 （当启动事务的主业务方法执行完以后，再执行其他业务方法时将进入txInServiceTransactionServer业务处理）
            if (nettyService.checkState()) {
                return txStartTransactionServer;
            } else {
                throw new Exception("tx-manager尚未链接成功,请检测tx-manager服务");
            }
        }


        /** 分布式事务已经开启，业务进行中 **/
        if (info.getTxTransactionLocal() != null || StringUtils.isNotEmpty(info.getTxGroupId())) {
            //检查socket通讯是否正常 （第一次执行时启动txRunningTransactionServer的业务处理控制，然后嵌套调用其他事务的业务方法时都并到txInServiceTransactionServer业务处理下）
            if (nettyService.checkState()) {
                if (info.getTxTransactionLocal() != null) {
                    return txDefaultTransactionServer;
                } else {
                    if(transactionControl.hasTransaction()) {//有事务业务的操作
                        return txRunningTransactionServer;
                    }else {
                        return txRunningNoTransactionServer;
                    }
                }
            } else {
                throw new Exception("tx-manager尚未链接成功,请检测tx-manager服务");
            }
        }
        /*********分布式事务处理逻辑*结束***********/

        return txDefaultTransactionServer;
    }
}
