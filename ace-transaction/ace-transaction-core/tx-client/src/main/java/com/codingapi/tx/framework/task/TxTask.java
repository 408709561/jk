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

package com.codingapi.tx.framework.task;

import com.lorne.core.framework.utils.task.IBack;
import com.lorne.core.framework.utils.task.Task;

/**
 * create by lorne on 2017/8/22
 */
public class TxTask extends Task{

    private Task task;


    public TxTask(Task task) {
        this.task = task;
    }

    @Override
    public boolean isNotify() {
        return task.isNotify();
    }

    @Override
    public boolean isRemove() {
        return task.isRemove();
    }

    @Override
    public boolean isAwait() {
        return task.isAwait();
    }

    @Override
    public int getState() {
        return task.getState();
    }

    @Override
    public void setState(int state) {
        task.setState(state);
    }

    @Override
    public String getKey() {
        return task.getKey();
    }

    @Override
    public void setKey(String key) {
        task.setKey(key);
    }

    @Override
    public IBack getBack() {
        return task.getBack();
    }

    @Override
    public void setBack(IBack back) {
        task.setBack(back);
    }

    @Override
    public Object execute(IBack back) {
        return task.execute(back);
    }

    @Override
    public void remove() {
        task.remove();

        boolean hasData = true;//true没有，false有

        String groupKey = getKey().split("_")[1];
        TaskGroup taskGroup =  TaskGroupManager.getInstance().getTaskGroup(groupKey);
        for(TxTask task: taskGroup.getTasks()){
            if(!task.isRemove()){
                hasData = false;
            }
        }

        if(hasData){
            TaskGroupManager.getInstance().removeKey(groupKey);
        }



    }

    @Override
    public void signalTask() {
        task.signalTask();
    }

    @Override
    public void signalTask(IBack back) {
        task.signalTask(back);
    }

    @Override
    public void awaitTask() {
        task.awaitTask();
    }

    @Override
    public void awaitTask(IBack back) {
        task.awaitTask(back);
    }
}
