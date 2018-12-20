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

package com.codingapi.tx.compensate.model;

/**
 * create by lorne on 2017/11/13
 */
public class CompensateInfo {


    private long currentTime;
    private String modelName;
    private String uniqueKey;
    private String data;
    private String methodStr;
    private String className;
    private String groupId;
    private String address;
    private long time;
    private String resJson;
    private int startError;

    private int state;


    public String toParamsString() {
        String postParam = "model=" + modelName + "&uniqueKey=" + uniqueKey + "" +
            "&address=" + address + "&currentTime=" + currentTime +
            "&data=" + data + "&time=" + time + "&groupId=" + groupId + "" +
            "&className=" + className + "&methodStr=" + methodStr+"&startError="+startError;
        return postParam;
    }

    public CompensateInfo() {
    }

    public CompensateInfo(long currentTime, String modelName, String uniqueKey, String data,
                          String methodStr, String className, String groupId, String address,
                          long time,int startError) {
        this.currentTime = currentTime;
        this.modelName = modelName;
        this.uniqueKey = uniqueKey;
        this.data = data;
        this.methodStr = methodStr;
        this.className = className;
        this.groupId = groupId;
        this.address = address;
        this.time = time;
        this.state = 0;
        this.startError =startError;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMethodStr() {
        return methodStr;
    }

    public void setMethodStr(String methodStr) {
        this.methodStr = methodStr;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getResJson() {
        return resJson;
    }

    public void setResJson(String resJson) {
        this.resJson = resJson;
    }
}
