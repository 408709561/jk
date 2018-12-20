

package com.github.wxiaoqi.security.auth.module.client.controller;

import com.github.wxiaoqi.security.auth.module.client.biz.ClientBiz;
import com.github.wxiaoqi.security.auth.module.client.entity.Client;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ace
 * @version 2017/12/26.
 */
@RestController
@RequestMapping("service")
public class ServiceController extends BaseController<ClientBiz,Client,String>{

    @RequestMapping(value = "/{id}/client", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyUsers(@PathVariable String id, String clients){
        baseBiz.modifyClientServices(id, clients);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/{id}/client", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<Client>> getUsers(@PathVariable String id){
        ObjectRestResponse<List<Client> > entityObjectRestResponse = new ObjectRestResponse<>();
        Object o = baseBiz.getClientServices(id);
        entityObjectRestResponse.data((List<Client>)o);
        return entityObjectRestResponse;
    }
}
