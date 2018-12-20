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

package com.codingapi.tm.api.controller;

import com.codingapi.tm.api.service.ApiTxManagerService;
import com.codingapi.tm.model.TxServer;
import com.codingapi.tm.model.TxState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lorne on 2017/7/1.
 */
@RestController
@RequestMapping("/tx/manager")
public class TxManagerController {

    @Autowired
    private ApiTxManagerService apiTxManagerService;


    @RequestMapping("/getServer")
    public TxServer getServer() {
        return apiTxManagerService.getServer();
    }


    @RequestMapping("/cleanNotifyTransaction")
    public int cleanNotifyTransaction(@RequestParam("groupId") String groupId,@RequestParam("taskId") String taskId) {
        return apiTxManagerService.cleanNotifyTransaction(groupId,taskId);
    }


    @RequestMapping("/sendMsg")
    public String sendMsg(@RequestParam("msg") String msg,@RequestParam("model") String model) {
        return apiTxManagerService.sendMsg(model,msg);
    }


    @RequestMapping("/sendCompensateMsg")
    public boolean sendCompensateMsg(@RequestParam("model") String model, @RequestParam("uniqueKey") String uniqueKey,
                                     @RequestParam("currentTime") long currentTime,
                                     @RequestParam("groupId") String groupId, @RequestParam("className") String className,
                                     @RequestParam("time") int time, @RequestParam("data") String data,
                                     @RequestParam("methodStr") String methodStr, @RequestParam("address") String address,
                                     @RequestParam("startError") int startError) {
        return apiTxManagerService.sendCompensateMsg(currentTime, groupId, model, address, uniqueKey, className, methodStr, data, time,startError);
    }



    @RequestMapping("/state")
    public TxState state() {
        return apiTxManagerService.getState();
    }
}
