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

import com.codingapi.tx.model.Request;
import com.lorne.core.framework.utils.task.ConditionUtils;
import com.lorne.core.framework.utils.task.IBack;
import com.lorne.core.framework.utils.task.Task;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * create by lorne on 2017/11/11
 */
public class SocketManager {

    private final static Logger logger = LoggerFactory.getLogger(SocketManager.class);

    private ChannelHandlerContext ctx;

    /**
     * 自动返回数据时间，必须要小于事务模块最大相应时间.(通过心跳获取)
     */

    private volatile int delay = 1;

    /**
     * false 未链接
     * true 连接中
     */
    private  volatile boolean netState = false;


    private static SocketManager manager = null;

    private Executor threadPool = Executors.newFixedThreadPool(max_size);

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(max_size);

    private final static int max_size = 50;


    public static SocketManager getInstance() {
        if (manager == null){
            synchronized (SocketManager.class){
                if(manager==null){
                    manager = new SocketManager();
                }
            }
        }
        return manager;
    }

    private SocketManager() {
    }


    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public boolean isNetState() {
        return netState;
    }

    public void setNetState(boolean netState) {
        this.netState = netState;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    private void sleepSend(Task task, Request request) {
        while (!task.isAwait() && !Thread.currentThread().interrupted()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        SocketUtils.sendMsg(ctx, request.toMsg());
        logger.info("send-msg->" + request.toMsg());
    }

    public String sendMsg(final Request request) {
        final String key = request.getKey();
        if (ctx != null && ctx.channel() != null && ctx.channel().isActive()) {
            final Task task = ConditionUtils.getInstance().createTask(key);
            ScheduledFuture future = executorService.schedule(new Runnable() {
                @Override
                public void run() {
                    Task task = ConditionUtils.getInstance().getTask(key);
                    if (task != null && !task.isNotify()) {
                        task.setBack(new IBack() {
                            @Override
                            public Object doing(Object... objs) throws Throwable {
                                return null;
                            }
                        });
                        task.signalTask();
                    }
                }
            }, delay, TimeUnit.SECONDS);

            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    sleepSend(task, request);
                }
            });

            task.awaitTask();

            if (!future.isDone()) {
                future.cancel(false);
            }

            try {
                Object msg = task.getBack().doing();
                return (String) msg;
            } catch (Throwable e) {
            } finally {
                task.remove();
            }
        }
        return null;

    }
}
