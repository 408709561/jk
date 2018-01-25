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


import java.util.ArrayList;
import java.util.List;

/**
 * create by lorne on 2017/8/22
 */
public class TaskGroup {

    private String key;

    private List<TxTask> tasks;

    private TxTask current;

    private int state;


    public TxTask getCurrent() {
        return current;
    }

    public void setCurrent(TxTask current) {
        this.current = current;
    }

    public String getKey() {
        return key;
    }


    public TaskGroup() {
        tasks = new ArrayList<>();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<TxTask> getTasks() {
        return tasks;
    }

    public void addTask(TxTask task) {
        tasks.add(task);
    }


    public boolean isAwait(){
        for(TxTask task: getTasks()){
            if(!task.isAwait()){
                return false;
            }
        }
        return true;
    }

    public boolean isRemove(){
        for(TxTask task: getTasks()){
            if(!task.isRemove()){
                return false;
            }
        }
        return true;
    }



    public void signalTask(){
        for(TxTask task: getTasks()){
            task.setState(state);
            task.signalTask();
        }
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
