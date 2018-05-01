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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Api(description = "请假任务模块", tags = "请假任务模块")
public class LeaveRest {
    @Autowired
    TaskService taskService;

    @Autowired
    @Lazy
    IUserFeign userFeign;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @PostMapping("/apply")
    @ApiOperation(value = "发起请假流程")
    public ObjectRestResponse applyLeave(String startDate, String endDate, String reason) {
        ExecutionEntity pi = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            // 创建流程发起人
            map.put("employee", BaseContextHandler.getUsername());
            // 写入流程相关数据
            map.put("reason", reason);
            map.put("startDate", new DateTime(startDate).toDate());
            map.put("endDate", new DateTime(endDate).toDate());
            // 请假流程key
            String processName = "process";
            //流程启动
            identityService.setAuthenticatedUserId(BaseContextHandler.getUsername());
            // 启动一个流程实例
            pi = (ExecutionEntity) runtimeService.startProcessInstanceByKey(processName, map);
            String taskId = pi.getTasks().get(0).getId();
            // 根据实例把流程往下推
            taskService.complete(taskId, map);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        if(pi!=null) {
            return new ObjectRestResponse().data(pi.getProcessInstanceId());
        }else{
            return new ObjectRestResponse().data("");
        }
    }

    @PostMapping("/approve")
    @ApiOperation(value = "审批请假流程")
    public ObjectRestResponse applyLeave(String processInstanceId, boolean agree) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("agree", agree);
        // 获取当前人拥有的流程岗位
        List<String> userFlowPositions = userFeign.getUserFlowPositions(BaseContextHandler.getUserID());
        // 获取当前流程具有审批的岗位
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());
        boolean flag = false;
        // 判断当前人是否需要相关审批岗位
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
        // 当前人具有相关审批权限,则可以审批请假流程
        if (flag) {
            task.setAssignee(BaseContextHandler.getUsername());
            taskService.saveTask(task);
            taskService.complete(task.getId(), map);
            return new ObjectRestResponse().data(true);
        }
        return new ObjectRestResponse().data(false);
    }

}
