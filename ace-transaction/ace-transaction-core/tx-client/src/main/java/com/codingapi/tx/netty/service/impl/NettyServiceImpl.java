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
import com.codingapi.tx.framework.utils.SocketManager;
import com.codingapi.tx.listener.service.TimeOutService;
import com.codingapi.tx.netty.handler.TransactionHandler;
import com.codingapi.tx.netty.service.NettyControlService;
import com.codingapi.tx.netty.service.NettyDistributeService;
import com.codingapi.tx.netty.service.NettyService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by lorne on 2017/6/30.
 */
@Service
public class NettyServiceImpl implements NettyService {


    @Autowired
    private NettyDistributeService nettyDistributeService;


    @Autowired
    private NettyControlService nettyControlService;

    @Autowired
    private TimeOutService timeOutService;

    private EventLoopGroup workerGroup;


    private static volatile boolean isStarting = false;


    private Logger logger = LoggerFactory.getLogger(NettyServiceImpl.class);

    @Override
    public synchronized void start() {
        if (isStarting) {
            return;
        }
        isStarting = true;
        nettyDistributeService.loadTxServer();

        String host = Constants.txServer.getHost();
        int port = Constants.txServer.getPort();
        final int heart = Constants.txServer.getHeart();
        int delay = Constants.txServer.getDelay();
        int autoCompensateLimit = Constants.txServer.getAutoCompensateLimit();

        final TransactionHandler transactionHandler = new TransactionHandler(nettyControlService, delay);

        timeOutService.loadOutTime(autoCompensateLimit);
        workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {

                    ch.pipeline().addLast("timeout", new IdleStateHandler(heart, heart, heart, TimeUnit.SECONDS));

                    ch.pipeline().addLast(new LengthFieldPrepender(4, false));
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));


                    ch.pipeline().addLast(transactionHandler);
                }
            });
            // Start the client.
            logger.info("连接manager-socket服务-> host:" + host + ",port:" + port);
            ChannelFuture future = b.connect(host, port); // (5)

            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (!channelFuture.isSuccess()) {
                        channelFuture.channel().eventLoop().schedule(new Runnable() {
                            @Override
                            public void run() {
                                isStarting = false;
                                start();
                            }
                        }, 5, TimeUnit.SECONDS);
                    }
                }
            });

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    @Override
    public synchronized void close() {
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
            workerGroup = null;

            SocketManager.getInstance().setNetState(false);
            isStarting = false;
        }
    }


    @Override
    public boolean checkState() {
        if (!SocketManager.getInstance().isNetState()) {
            logger.error("socket服务尚未建立连接成功,将在此等待2秒.");
            try {
                Thread.sleep(1000 * 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!SocketManager.getInstance().isNetState()) {
                logger.error("socket还未连接成功,请检查TxManager服务后再试.");
                return false;
            }
        }

        return true;
    }


}
