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

package com.github.wxiaoqi.security.wf.activiti.rest.editor.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.explorer.util.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程模型Model操作相关
 * Created by chenhai on 2017/5/23.
 */
@Api(description = "流程模型Model操作相关", tags = {"modeler"})
@RestController
@RequestMapping("models")
public class ModelController {

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RepositoryService repositoryService;

    /**
     * 新建一个空模型
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation(value = "新建一个空模型")
    @PostMapping
    public Map<String,Object> newModel(String name,String key,String description) throws UnsupportedEncodingException {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //初始化一个空模型
        Model model = repositoryService.newModel();
        int revision = 1;
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
//        return new ModelAndView("redirect:/modeler.html?modelId=" + id);
        return success();
    }

    /**
     * 获取所有模型
     *
     * @return
     */
    @ApiOperation(value = "获取所有模型")
    @GetMapping
    public List<Model> modelList() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        return repositoryService.createModelQuery().orderByCreateTime().desc().list();
    }

    /**
     * 删除模型
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除模型")
    @DeleteMapping("{id}")
    public Object deleteModel(@PathVariable("id") String id) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteModel(id);
        return success();
    }

    /**
     * 发布模型为流程定义
     *
     * @param id
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "发布模型为流程定义")
    @PostMapping("{id}/deployment")
    public Object deploy(@PathVariable("id") String id) throws Exception {

        //获取模型
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Model modelData = repositoryService.getModel(id);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            return failed("模型数据为空，请先设计流程并成功保存，再进行发布。");
        }

        JsonNode modelNode = new ObjectMapper().readTree(bytes);

        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0) {
            return failed("数据模型不符要求，请至少设计一条主线流程。");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"));

        Deployment deployment = deploymentBuilder.deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);

        return success();
    }

    @ApiOperation(value = "上传一个已有模型")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public void deployUploadedFile(
            @RequestParam("uploadfile") MultipartFile uploadfile) {
        InputStreamReader in = null;
        try {
            try {
                boolean validFile = false;
                String fileName = uploadfile.getOriginalFilename();
                if (fileName.endsWith(".bpmn20.xml") || fileName.endsWith(".bpmn")) {
                    validFile = true;

                    XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
                    in = new InputStreamReader(new ByteArrayInputStream(uploadfile.getBytes()), "UTF-8");
                    XMLStreamReader xtr = xif.createXMLStreamReader(in);
                    BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

                    if (bpmnModel.getMainProcess() == null || bpmnModel.getMainProcess().getId() == null) {
//                        notificationManager.showErrorNotification(Messages.MODEL_IMPORT_FAILED,
//                                i18nManager.getMessage(Messages.MODEL_IMPORT_INVALID_BPMN_EXPLANATION));
                        System.out.println("err1");
                    } else {

                        if (bpmnModel.getLocationMap().isEmpty()) {
//                            notificationManager.showErrorNotification(Messages.MODEL_IMPORT_INVALID_BPMNDI,
//                                    i18nManager.getMessage(Messages.MODEL_IMPORT_INVALID_BPMNDI_EXPLANATION));
                            System.out.println("err2");
                        } else {

                            String processName = null;
                            if (StringUtils.isNotEmpty(bpmnModel.getMainProcess().getName())) {
                                processName = bpmnModel.getMainProcess().getName();
                            } else {
                                processName = bpmnModel.getMainProcess().getId();
                            }
                            Model modelData;
                            modelData = repositoryService.newModel();
                            ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
                            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processName);
                            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
                            modelData.setMetaInfo(modelObjectNode.toString());
                            modelData.setName(processName);

                            repositoryService.saveModel(modelData);

                            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
                            ObjectNode editorNode = jsonConverter.convertToJson(bpmnModel);

                            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
                        }
                    }
                } else {
//                    notificationManager.showErrorNotification(Messages.MODEL_IMPORT_INVALID_FILE,
//                            i18nManager.getMessage(Messages.MODEL_IMPORT_INVALID_FILE_EXPLANATION));
                    System.out.println("err3");
                }
            } catch (Exception e) {
                String errorMsg = e.getMessage().replace(System.getProperty("line.separator"), "<br/>");
//                notificationManager.showErrorNotification(Messages.MODEL_IMPORT_FAILED, errorMsg);
                System.out.println("err4");
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
//                    notificationManager.showErrorNotification("Server-side error", e.getMessage());
                    System.out.println("err5");
                }
            }
        }
    }


    private Map<String,Object> success() {
        Map<String,Object> map = new HashMap();
        map.put("status",true);
        map.put("reason","操作成功");
        return map;
    }

    private Map<String, Object> failed(String reason) {
        Map<String,Object> map = new HashMap();
        map.put("status",false);
        map.put("reason","操作失败："+reason);
        return map;
    }

}
