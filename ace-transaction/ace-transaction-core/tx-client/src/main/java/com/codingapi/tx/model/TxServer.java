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

package com.codingapi.tx.model;


import com.alibaba.fastjson.JSONObject;

/**
 * Created by lorne on 2017/6/30.
 */
public class TxServer {

    private int port;
    private String host;
    private int heart;
    private int delay;
    private int autoCompensateLimit;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getAutoCompensateLimit() {
		return autoCompensateLimit;
	}

	public void setAutoCompensateLimit(int autoCompensateLimit) {
		this.autoCompensateLimit = autoCompensateLimit;
	}

	@Override
    public String toString() {
        return "host:" + host + ",port:" + port + ",heart:" + heart + ",delay:" + delay + ",autoCompensateLimit:" + autoCompensateLimit;
    }

    public static TxServer parser(String json) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            TxServer txServer = new TxServer();
            txServer.setPort(jsonObject.getInteger("port"));
            txServer.setHost(jsonObject.getString("ip"));
            txServer.setHeart(jsonObject.getInteger("heart"));
            txServer.setDelay(jsonObject.getInteger("delay"));
            txServer.setAutoCompensateLimit(jsonObject.getInteger("autoCompensateLimit"));
            return txServer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
