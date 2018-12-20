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
