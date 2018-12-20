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

package com.codingapi.tx.netty.service;

import com.codingapi.tx.aop.bean.TxTransactionInfo;
import com.codingapi.tx.model.TxGroup;


/**
 * Created by lorne on 2017/6/7.
 */
public interface MQTxManagerService {


    /**
     * 上传模块信息
     */
    void uploadModelInfo();

    /**
     * 创建事务组
     *
     * @return  事务组TxGroup
     */
    TxGroup createTransactionGroup();


    /**
     * 添加事务组子对象
     * @param groupId   事务组id
     * @param taskId    任务Id
     * @param isGroup   是否合并到事务组 true合并 false不合并
     * @param methodStr   方法参数列表
     * @return  事务组TxGroup
     */
    TxGroup addTransactionGroup(String groupId, String taskId, boolean isGroup, String methodStr);


    /**
     * 关闭事务组-进入事务提交第一阶段
     *
     * @param groupId   事务组id
     * @param state     提交或者回滚 1提交0回滚
     * @return 1 成功 0失败 -1 事务强制回滚
     */
    int closeTransactionGroup(String groupId, int state);


    /**
     * 检查事务状态 通过netty管道
     * @param groupId   事务组id
     * @param taskId    任务id
     * @return  事务状态
     */
    int cleanNotifyTransaction(String groupId, String taskId);



    /**
     * 检查并清理事务数据
     * @param groupId   事务组id
     * @param waitTaskId    任务id
     * @return  事务状态
     */
    int cleanNotifyTransactionHttp(String groupId, String waitTaskId);

    /**
     * 记录补偿事务数据到tm
     * @param groupId   事务组Id
     * @param time  执行时间
     * @param info  事务信息
     * @param startError 启动模块db执行异常
     */
    void sendCompensateMsg(String groupId, long time, TxTransactionInfo info,int startError);


    /**
     * 获取TM服务地址
     * @return txServer
     */
    String httpGetServer();

}
