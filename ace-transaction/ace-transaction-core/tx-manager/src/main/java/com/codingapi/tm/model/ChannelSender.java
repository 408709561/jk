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

package com.codingapi.tm.model;

import com.codingapi.tm.framework.utils.SocketUtils;
import com.lorne.core.framework.utils.http.HttpUtils;
import com.lorne.core.framework.utils.task.IBack;
import com.lorne.core.framework.utils.task.Task;
import io.netty.channel.Channel;
import org.apache.commons.lang.StringUtils;

/**
 * create by lorne on 2017/8/7
 */
public class ChannelSender {


    private Channel channel;

    private String address;

    private String modelName;

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void send(String msg){
        if(channel!=null){
            SocketUtils.sendMsg(channel,msg);
        }

    }

    public void send(String msg,Task task){
        if(channel!=null){
            SocketUtils.sendMsg(channel,msg);
        }else{
            String url = String.format("http://%s/tx/manager/sendMsg",address);
            final String res = HttpUtils.post(url,"msg="+msg+"&model="+modelName);
            if(StringUtils.isNotEmpty(res)){
                if(task!=null) {
                    task.setBack(new IBack() {
                        @Override
                        public Object doing(Object... objs) throws Throwable {
                            return res;
                        }
                    });
                    task.signalTask();
                }
            }else{
                if(task!=null) {
                    task.setBack(new IBack() {
                        @Override
                        public Object doing(Object... objs) throws Throwable {
                            return "-2";
                        }
                    });
                    task.signalTask();
                }
            }
        }

    }
}
