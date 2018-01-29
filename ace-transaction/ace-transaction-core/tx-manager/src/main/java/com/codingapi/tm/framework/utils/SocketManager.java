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

package com.codingapi.tm.framework.utils;

import com.codingapi.tm.Constants;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by lorne on 2017/6/30.
 */
public class SocketManager {

    /**
     * 最大连接数
     */
    private int maxConnection = Constants.maxConnection;

    /**
     * 当前连接数
     */
    private int nowConnection;

    /**
     * 允许连接请求 true允许 false拒绝
     */
    private boolean allowConnection = true;

    private List<Channel> clients = null;

    private Map<String,String> lines = null;

    private static SocketManager manager = null;

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


    public Channel getChannelByModelName(String name) {
        for (Channel channel : clients) {
            String modelName = channel.remoteAddress().toString();

            if (modelName.equals(name)) {
                return channel;
            }
        }
        return null;
    }

    private SocketManager() {
        clients = new CopyOnWriteArrayList<Channel>();
        lines = new ConcurrentHashMap<>();
    }

    public void addClient(Channel client) {
        clients.add(client);
        nowConnection = clients.size();

        allowConnection = (maxConnection != nowConnection);
    }

    public void removeClient(Channel client) {
        clients.remove(client);
        nowConnection = clients.size();

        allowConnection = (maxConnection != nowConnection);
    }


    public int getMaxConnection() {
        return maxConnection;
    }

    public int getNowConnection() {
        return nowConnection;
    }

    public boolean isAllowConnection() {
        return allowConnection;
    }

    public void outLine(String modelName) {
        lines.remove(modelName);
    }

    public void onLine(String modelName, String uniqueKey) {
        lines.put(modelName,uniqueKey);
    }

    public Channel getChannelByUniqueKey(String uniqueKey) {
        for (Channel channel : clients) {
            String modelName = channel.remoteAddress().toString();
            String value  = lines.get(modelName);
            if (uniqueKey.equals(value)) {
                return channel;
            }
        }
        return null;
    }
}
