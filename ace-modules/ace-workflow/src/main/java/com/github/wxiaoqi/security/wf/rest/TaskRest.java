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

package com.github.wxiaoqi.security.wf.rest;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.wf.feign.IUserFeign;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author ace
 * @create 2018/4/4.
 */
@RestController
@RequestMapping("tasks")
@CheckUserToken
public class TaskRest {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    ManagementService managementService;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;

    @Autowired
    ProcessEngineFactoryBean processEngine;

    @Autowired
    HistoryService historyService;

    @Autowired
    TaskService taskService;

    @Autowired
    @Lazy
    IUserFeign userFeign;

    /**
     * 获取个人发起任务
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/apply")
    public TableResultResponse<Task> getMyApplyTasks(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int pageSize) throws XMLStreamException {
        if (pageNum <= 1) {
            pageNum = 1;
        }
        if (pageSize <= 1) {
            pageSize = 0xf423f;
        }
        int start = (pageNum - 1) * pageSize;
        int limit = pageSize;
        long count = taskService.createTaskQuery().processVariableValueEquals("employee", BaseContextHandler.getUsername()).count();
        List<Task> tasks = taskService.createTaskQuery().processVariableValueEquals("employee", BaseContextHandler.getUsername()).listPage(start, limit);
        List<String> result = new ArrayList<>();
        tasks.forEach(task -> {
            result.add(task.toString());
        });
        return new TableResultResponse(count, result);
    }

    /**
     * 获取历史
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/finish")
    public TableResultResponse<Task> getMyDealTasks(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        if (pageNum <= 1) {
            pageNum = 1;
        }
        if (pageSize <= 1) {
            pageSize = 0xf423f;
        }
        int start = (pageNum - 1) * pageSize;
        int limit = pageSize;

        long count = historyService.createHistoricTaskInstanceQuery().taskAssignee(BaseContextHandler.getUsername()).count();
        List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery().taskAssignee(BaseContextHandler.getUsername()).listPage(start, limit);
        return new TableResultResponse(count, tasks);
    }

    /**
     * 获取待审批流程
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/todo")
    public TableResultResponse<Task> getMyApproveTasks(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        if (pageNum <= 1) {
            pageNum = 1;
        }
        if (pageSize <= 1) {
            pageSize = 0xf423f;
        }
        int start = (pageNum - 1) * pageSize;
        int limit = pageSize;
        // 获取个人已签收任务
        long count = taskService.createTaskQuery().taskAssignee(BaseContextHandler.getUsername()).count();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(BaseContextHandler.getUsername()).listPage(start, limit);
        List<String> result = new ArrayList<>();
        tasks.forEach(task -> {
            result.add(task.toString());
        });
        // 获取岗位待签收任务
        List<String> groups = userFeign.getUserFlowPositions(BaseContextHandler.getUserID());
        if (groups != null && groups.size() > 0) {
            tasks = taskService.createTaskQuery().taskCandidateGroupIn(groups).listPage(start, limit);
            tasks.forEach(task -> {
                result.add(task.toString());
            });
        }
        // 获取个人待签收任务
        tasks = taskService.createTaskQuery().taskCandidateUser(BaseContextHandler.getUsername()).listPage(start, limit);
        tasks.forEach(task -> {
            result.add(task.toString());
        });
        return new TableResultResponse(count, result);
    }

    @RequestMapping("/diagram")
    @IgnoreUserToken
    public void getTaskFlowPng(String processInstanceId, HttpServletResponse response) throws Exception {
        String fileName = "diagram";
        InputStream inputStream = generateFLowPng(processInstanceId, fileName);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".png\"");
        response.setHeader("Content-Length", "" + inputStream.available());
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    /**
     * @return Iterator<FlowElement>
     * @throws
     * @throwsXMLStreamException 查询流程节点
     * @author：tuzongxun
     * @Title: findFlow
     * @param@return
     * @date Mar 21, 20169:31:42 AM
     */
    public Iterator<FlowElement> findFlow(String processDefId)
            throws XMLStreamException {
        List<ProcessDefinition> lists = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(processDefId)
                .orderByProcessDefinitionVersion().desc().list();
        ProcessDefinition processDefinition = lists.get(0);
        processDefinition.getCategory();
        String resourceName = processDefinition.getResourceName();
        InputStream inputStream = repositoryService.getResourceAsStream(
                processDefinition.getDeploymentId(), resourceName);
        BpmnXMLConverter converter = new BpmnXMLConverter();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(inputStream);
        BpmnModel bpmnModel = converter.convertToBpmnModel(reader);
        Process process = bpmnModel.getMainProcess();
        Collection<FlowElement> elements = process.getFlowElements();
        Iterator<FlowElement> iterator = elements.iterator();
        return iterator;
    }


    private InputStream generateFLowPng(String processInstanceId, String fileName) throws Exception {
        //获取历史流程实例
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

        List<HistoricActivityInstance> highLightedActivitList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        //高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<String>();
        //高亮线路id集合
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity, highLightedActivitList);

        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().processInstanceId(processInstanceId).singleResult();// 执行实例
        if (execution == null) {
            highLightedActivitis.add(highLightedActivitList.get(highLightedActivitList.size() - 1).getActivityId());
        } else {
            highLightedActivitis.add(execution.getActivityId());
        }

        //中文显示的是口口口，设置字体就好了
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis, highLightedFlows, 1.0);
        System.out.println("method startActivityDemo end....");
        return imageStream;
    }

    /**
     * 获取需要高亮的线
     *
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }


}
