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

import com.codingapi.tx.annotation.TxTransaction;
import com.codingapi.tx.aop.bean.TxTransactionInfo;
import com.codingapi.tx.aop.bean.TxTransactionLocal;
import com.codingapi.tx.aop.service.AspectBeforeService;
import com.codingapi.tx.aop.service.TransactionServer;
import com.codingapi.tx.aop.service.TransactionServerFactoryService;
import com.codingapi.tx.model.TransactionInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * Created by lorne on 2017/7/1.
 */
@Service
public class AspectBeforeServiceImpl implements AspectBeforeService {

    @Autowired
    private TransactionServerFactoryService transactionServerFactoryService;


    private Logger logger = LoggerFactory.getLogger(AspectBeforeServiceImpl.class);


    public Object around(String groupId,int maxTimeOut, ProceedingJoinPoint point) throws Throwable {

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> clazz = point.getTarget().getClass();
        Object[] args = point.getArgs();
        Method thisMethod = clazz.getMethod(method.getName(), method.getParameterTypes());

        TxTransaction transaction = thisMethod.getAnnotation(TxTransaction.class);

        TxTransactionLocal txTransactionLocal = TxTransactionLocal.current();

        logger.info("around--> groupId-> " +groupId+",txTransactionLocal->"+txTransactionLocal);

        TransactionInvocation invocation = new TransactionInvocation(clazz, thisMethod.getName(), thisMethod.toString(), args, method.getParameterTypes());

        TxTransactionInfo info = new TxTransactionInfo(transaction,txTransactionLocal,invocation,groupId,maxTimeOut);

        TransactionServer server = transactionServerFactoryService.createTransactionServer(info);

        return server.execute(point, info);
    }
}
