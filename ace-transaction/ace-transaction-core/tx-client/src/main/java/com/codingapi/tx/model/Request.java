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

import com.lorne.core.framework.utils.KidUtils;

/**
 * Created by lorne on 2017/6/30.
 */
public class Request {

    /**
     * key
     */
    private String key;
    /**
     * action
     */
    private String action;
    /**
     * params
     */
    private String params;


    public Request(String action, String params) {
        this.action = action;
        this.params = params;
        this.key = KidUtils.generateShortUuid();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String toMsg() {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("a", getAction());
//        jsonObject.put("k", getKey());
//        jsonObject.put("p", getParams());
        String json = "{\"a\":\"" + getAction() + "\",\"k\":\"" + getKey() + "\",\"p\":" + getParams() + "}";
        return json;
    }
}
