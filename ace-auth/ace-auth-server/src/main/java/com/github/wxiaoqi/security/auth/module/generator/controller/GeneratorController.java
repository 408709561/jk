

package com.github.wxiaoqi.security.auth.module.generator.controller;

import com.github.wxiaoqi.security.auth.module.client.biz.ClientBiz;
import com.github.wxiaoqi.security.auth.module.client.entity.Client;
import com.github.wxiaoqi.security.auth.module.generator.service.GeneratorService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ace
 * @version 2018/1/17.
 */
@RestController
@RequestMapping("generator")
public class GeneratorController {
    @Autowired
    private GeneratorService generatorService;
    @Autowired
    private ClientBiz clientBiz;

    /**
     * 生成代码
     */
    @RequestMapping("/build")
    public void code(String id, String packageName, boolean zipkin, boolean tx, HttpServletResponse response) throws IOException {
        Client client = clientBiz.selectById(id);
        if (client != null) {
            byte[] data = generatorService.buildProject(client, packageName, zipkin, tx);
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + client.getCode() + ".zip\"");
            response.setHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IOUtils.write(data, response.getOutputStream());
        }
    }
}
