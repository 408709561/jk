
package com.github.wxiaoqi.security.auth.module.generator.service;

import com.github.wxiaoqi.security.auth.module.client.entity.Client;
import com.github.wxiaoqi.security.auth.module.generator.util.GeneratorUtils;
import com.github.ag.core.context.BaseContextHandler;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.zip.ZipOutputStream;

/**
 * @author ace
 * @version 2018/1/17.
 */
@Component
public class GeneratorService {
    /**
     * 生成工程
     * @return
     * @param client
     * @param packageName
     * @param zipkin
     * @param tx
     */
    public byte[] buildProject(Client client, String packageName, Boolean zipkin, Boolean tx){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        HashMap<String, Object> map = new HashMap<>();
        map.put("package", packageName);
        map.put("author", BaseContextHandler.getUsername());
        map.put("description", client.getDescription());
        // 决定服务名\maven模块名\主文件夹名
        map.put("clientId", client.getCode());
        map.put("clientSecret", client.getSecret());
        map.put("zipkin",zipkin);
        map.put("tx",tx);
        GeneratorUtils.buildProject(map,zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
