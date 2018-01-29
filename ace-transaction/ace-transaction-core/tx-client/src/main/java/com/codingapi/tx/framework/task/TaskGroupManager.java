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

import com.lorne.core.framework.utils.task.ConditionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create by lorne on 2017/8/22
 */
public class TaskGroupManager {

    private Map<String, TaskGroup> taskMap = new ConcurrentHashMap<String, TaskGroup>();

    private static TaskGroupManager instance = null;

    private TaskGroupManager(){}

    public static TaskGroupManager getInstance() {
        if (instance == null) {
            synchronized (TaskGroupManager.class) {
                if(instance==null){
                    instance = new TaskGroupManager();
                }
            }
        }
        return instance;
    }

    public TaskGroup createTask(String key,String type) {
        TaskGroup taskGroup = getTaskGroup(key);
        if(taskGroup==null){
            taskGroup = new TaskGroup();
        }
        taskGroup.setKey(key);

        String taskKey = type+"_"+key;

        TxTask task =  new TxTask(ConditionUtils.getInstance().createTask(taskKey));
        taskGroup.setCurrent(task);
        taskGroup.addTask(task);
        taskMap.put(key, taskGroup);
        return taskGroup;
    }

    public TaskGroup getTaskGroup(String key) {
        return taskMap.get(key);
    }


    public TxTask getTask(String key,String type) {
        String taskKey = type+"_"+key;
        TaskGroup txGroup =  taskMap.get(key);
        if(txGroup!=null){
            for(TxTask task:txGroup.getTasks()){
                if(taskKey.equals(task.getKey())){
                    return task;
                }
            }
        }
        return null;
    }


    public void removeKey(String key) {
        if (StringUtils.isNotEmpty(key)) {
            taskMap.remove(key);
        }
    }



}
