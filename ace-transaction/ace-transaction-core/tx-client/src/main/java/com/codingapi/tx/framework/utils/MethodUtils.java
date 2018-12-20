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

package com.codingapi.tx.framework.utils;


import com.codingapi.tx.model.TransactionInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * create by lorne on 2017/11/13
 */
public class MethodUtils {

    private static final Logger logger = LoggerFactory.getLogger(MethodUtils.class);

    public static boolean invoke(ApplicationContext spring, TransactionInvocation invocation) {
        try {
            Object bean = spring.getBean(invocation.getTargetClazz());
            Object res = org.apache.commons.lang.reflect.MethodUtils.invokeMethod(bean, invocation.getMethod(), invocation.getArgumentValues(), invocation.getParameterTypes());
            logger.info("事务补偿执行---> className:" + invocation.getTargetClazz() + ",methodName::" + invocation.getMethod() + ",args:" + invocation.getArgumentValues() + ",res:" + res);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
