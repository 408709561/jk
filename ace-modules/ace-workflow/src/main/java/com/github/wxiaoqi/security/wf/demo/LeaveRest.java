/*
 *
 *  *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>
 *
 *  *  AG-Enterprise 企业版源码
 *  *  郑重声明:
 *  *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  *  老A将追究授予人和传播人的法律责任!
 *
 *  *  This program is free software; you can redistribute it and/or modify
 *  *  it under the terms of the GNU General Public License as published by
 *  *  the Free Software Foundation; either version 2 of the License, or
 *  *  (at your option) any later version.
 *
 *  *  This program is distributed in the hope that it will be useful,
 *  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  GNU General Public License for more details.
 *
 *  *  You should have received a copy of the GNU General Public License along
 *  *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package com.github.wxiaoqi.security.wf.demo;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.wf.feign.IUserFeign;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author ace
 * @create 2018/4/5.
 */
@RequestMapping("leave")
@RestController
@CheckUserToken
public class LeaveRest {
    @Autowired
    TaskService taskService;

    @Autowired
    @Lazy
    IUserFeign userFeign;

    @Autowired
    protected RuntimeService runtimeService;

    @RequestMapping("/apply")
    public ObjectRestResponse applyLeave(Date start, Date end, String reason) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("employee", BaseContextHandler.getUsername());
        map.put("reason", reason);
        map.put("start",start);
        map.put("end",end);
        // 请假流程key
        String processName = "process";
        //流程启动
        ExecutionEntity pi1 = (ExecutionEntity) runtimeService.startProcessInstanceByKey(processName, map);
        String taskId = pi1.getTasks().get(0).getId();
        taskService.complete(taskId, map);
        //完成第一步申请
        return new ObjectRestResponse().data(pi1.getProcessInstanceId());
    }

    @RequestMapping("/approve")
    public ObjectRestResponse applyLeave(String processInstanceId, boolean agree) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("agree", agree);
        System.out.println(BaseContextHandler.getUsername());
        List<String> userFlowPositions = userFeign.getUserFlowPositions(BaseContextHandler.getUserID());
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());
        boolean flag = false;
        for (IdentityLink link : identityLinksForTask) {
            long count = userFlowPositions.stream().filter(new Predicate<String>() {
                @Override
                public boolean test(String s) {
                    return s.equals(link.getGroupId());
                }
            }).count();
            if (count > 0) {
                flag = true;
                break;
            }
        }
        if (flag) {
            task.setAssignee(BaseContextHandler.getUsername());
            taskService.saveTask(task);
            taskService.complete(task.getId(), map);
            return new ObjectRestResponse().data(true);
        }
        return new ObjectRestResponse().data(false);
    }
}
