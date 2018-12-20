

package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.GateLogBiz;
import com.github.wxiaoqi.security.admin.entity.GateLog;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-07-01 20:32
 */
@Controller
@CheckUserToken
@CheckClientToken
@RequestMapping("gateLog")
@Api(tags = "日志管理")
public class GateLogController extends BaseController<GateLogBiz,GateLog,Integer> {
//    @RequestMapping(value = "/page",method = RequestMethod.GET)
//    @ResponseBody
//    public TableResultResponse<GateLog> page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1")int offset, String name){
//        Example example = new Example(GateLog.class);
//        if(StringUtils.isNotBlank(name)) {
//            example.createCriteria().andLike("menu", "%" + name + "%");
//        }
//        int count = baseBiz.selectCountByExample(example);
//        PageHelper.startPage(offset, limit);
//        return new TableResultResponse<GateLog>(count,baseBiz.selectByExample(example));
//    }
}
