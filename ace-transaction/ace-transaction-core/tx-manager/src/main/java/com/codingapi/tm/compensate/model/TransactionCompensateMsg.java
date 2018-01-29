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

package com.codingapi.tm.compensate.model;

import com.codingapi.tm.netty.model.TxGroup;

/**
 * create by lorne on 2017/11/11
 */
public class TransactionCompensateMsg {

    private long currentTime;
    private String groupId;
    private String model;
    private String address;
    private String uniqueKey;
    private String className;
    private String methodStr;
    private String data;
    private int time;
    private int startError;

    private TxGroup txGroup;

    private int state;


    public TransactionCompensateMsg(long currentTime, String groupId, String model, String address,
                                    String uniqueKey, String className,
                                    String methodStr, String data, int time, int state,int startError) {
        this.currentTime = currentTime;
        this.groupId = groupId;
        this.model = model;
        this.uniqueKey = uniqueKey;
        this.className = className;
        this.methodStr = methodStr;
        this.data = data;
        this.time = time;
        this.address = address;
        this.state = state;
        this.startError = startError;
    }

    public int getStartError() {
        return startError;
    }

    public void setStartError(int startError) {
        this.startError = startError;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TxGroup getTxGroup() {
        return txGroup;
    }

    public void setTxGroup(TxGroup txGroup) {
        this.txGroup = txGroup;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getMethodStr() {
        return methodStr;
    }

    public void setMethodStr(String methodStr) {
        this.methodStr = methodStr;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
