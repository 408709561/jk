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

import com.alibaba.fastjson.JSONObject;
import com.codingapi.tx.aop.bean.TxCompensateLocal;
import com.codingapi.tx.aop.bean.TxTransactionInfo;
import com.codingapi.tx.compensate.model.CompensateInfo;
import com.codingapi.tx.compensate.service.CompensateService;
import com.codingapi.tx.config.ConfigReader;
import com.codingapi.tx.framework.utils.SerializerUtils;
import com.codingapi.tx.framework.utils.SocketManager;
import com.codingapi.tx.listener.service.ModelNameService;
import com.codingapi.tx.model.Request;
import com.codingapi.tx.model.TxGroup;
import com.codingapi.tx.netty.service.MQTxManagerService;
import com.codingapi.tx.netty.service.TxManagerHttpRequestHelper;
import com.lorne.core.framework.utils.encode.Base64Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lorne on 2017/6/30.
 */
@Service
public class MQTxManagerServiceImpl implements MQTxManagerService {


    @Autowired
    private ModelNameService modelNameService;

    @Autowired
    private ConfigReader configReader;

    @Autowired
    private CompensateService compensateService;

    @Autowired
    private TxManagerHttpRequestHelper managerHelper;


    @Override
    public TxGroup createTransactionGroup() {
        JSONObject jsonObject = new JSONObject();
        TxCompensateLocal compensateLocal = TxCompensateLocal.current();
        if (compensateLocal != null) {
            jsonObject.put("g", compensateLocal.getGroupId());
        }
        Request request = new Request("cg", jsonObject.toString());
        String json = SocketManager.getInstance().sendMsg(request);
        return TxGroup.parser(json);
    }

    @Override
    public TxGroup addTransactionGroup(String groupId, String taskId, boolean isGroup, String methodStr) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("g", groupId);
        jsonObject.put("t", taskId);
        jsonObject.put("ms", methodStr);
        jsonObject.put("s", isGroup ? 1 : 0);
        Request request = new Request("atg", jsonObject.toString());
        String json =  SocketManager.getInstance().sendMsg(request);
        return TxGroup.parser(json);
    }


    @Override
    public int closeTransactionGroup(final String groupId, final int state) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("g", groupId);
        jsonObject.put("s", state);
        Request request = new Request("ctg", jsonObject.toString());
        String json =  SocketManager.getInstance().sendMsg(request);
        try {
            return Integer.parseInt(json);
        }catch (Exception e){
            return 0;
        }
    }


    @Override
    public void uploadModelInfo() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("m", modelNameService.getModelName());
        jsonObject.put("i", modelNameService.getIpAddress());
        jsonObject.put("u", modelNameService.getUniqueKey());
        Request request = new Request("umi", jsonObject.toString());
        String json = SocketManager.getInstance().sendMsg(request);
    }

    @Override
    public int cleanNotifyTransaction(String groupId, String taskId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("g", groupId);
        jsonObject.put("t", taskId);
        Request request = new Request("ckg", jsonObject.toString());
        String json =  SocketManager.getInstance().sendMsg(request);
        try {
            return Integer.parseInt(json);
        }catch (Exception e){
            return -2;
        }
    }


    @Override
    public int cleanNotifyTransactionHttp(String groupId, String waitTaskId) {
        String url = configReader.getTxUrl() + "cleanNotifyTransactionHttp?groupId=" + groupId + "&taskId=" + waitTaskId;
        String clearRes = managerHelper.httpGet(url);
        if(clearRes==null){
            return -1;
        }
        return  clearRes.contains("true") ? 1 : 0;
    }


    @Override
    public String httpGetServer() {
        String url = configReader.getTxUrl() + "getServer";
        return managerHelper.httpGet(url);
    }

    @Override
    public void sendCompensateMsg(String groupId, long time, TxTransactionInfo info,int startError) {

        String modelName = modelNameService.getModelName();
        String uniqueKey = modelNameService.getUniqueKey();
        String address = modelNameService.getIpAddress();


        byte[] serializers =  SerializerUtils.serializeTransactionInvocation(info.getInvocation());
        String data = Base64Utils.encode(serializers);

        String className = info.getInvocation().getTargetClazz().getName();
        String methodStr = info.getInvocation().getMethodStr();
        long currentTime = System.currentTimeMillis();


        CompensateInfo compensateInfo = new CompensateInfo(currentTime, modelName, uniqueKey, data, methodStr, className, groupId, address, time,startError);

        String json = managerHelper.httpPost(configReader.getTxUrl() + "sendCompensateMsg", compensateInfo.toParamsString());

        compensateInfo.setResJson(json);

        //记录本地日志
        compensateService.saveLocal(compensateInfo);

    }
}
