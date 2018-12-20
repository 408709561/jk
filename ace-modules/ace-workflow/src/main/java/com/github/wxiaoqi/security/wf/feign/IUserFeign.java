


package com.github.wxiaoqi.security.wf.feign;

import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ace
 * @create 2018/2/11.
 */
@FeignClient(value = "ace-admin",configuration = FeignApplyConfiguration.class)
public interface IUserFeign {
    /**
     * 获取当前用户工作流的岗位Id列表
     * @return
     */
    @RequestMapping(value="/user/flowPosition",method = RequestMethod.GET)
    List<String> getUserFlowPositions(@RequestParam("userId") String userId);
}
