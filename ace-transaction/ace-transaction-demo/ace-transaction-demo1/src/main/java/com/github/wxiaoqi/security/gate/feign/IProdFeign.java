

package com.github.wxiaoqi.security.gate.feign;

import com.github.wxiaoqi.security.gate.config.FeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ace
 * @version 2018/1/18.
 */
@FeignClient(value = "ace-transaction-demo2",configuration = FeignConfiguration.class)
public interface IProdFeign {
    @RequestMapping("/prod/test")
    public void test();
}
