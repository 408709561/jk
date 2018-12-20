

package com.github.wxiaoqi.security.dict.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.dict.biz.DictValueBiz;
import com.github.wxiaoqi.security.dict.entity.DictValue;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("dictValue")
@CheckClientToken
@CheckUserToken
@Api(tags = "字典值服务",description = "字典值服务")
public class DictValueController extends BaseController<DictValueBiz,DictValue,String> {
    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/type/{code}",method = RequestMethod.GET)
    public TableResultResponse<DictValue> getDictValueByDictTypeCode(@PathVariable("code") String code){
        Example example = new Example(DictValue.class);
        example.createCriteria().andLike("code",code+"%");
        List<DictValue> dictValues = this.baseBiz.selectByExample(example).stream().sorted(new Comparator<DictValue>() {
            @Override
            public int compare(DictValue o1, DictValue o2) {
                return o1.getOrderNum() - o2.getOrderNum();
            }
        }).collect(Collectors.toList());
        return new TableResultResponse<DictValue>(dictValues.size(),dictValues);
    }

    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/feign/{code}",method = RequestMethod.GET)
    public Map<String,String> getDictValueByCode(@PathVariable("code") String code){
        Example example = new Example(DictValue.class);
        example.createCriteria().andLike("code",code+"%");
        List<DictValue> dictValues = this.baseBiz.selectByExample(example);
        Map<String, String> result = dictValues.stream().collect(
                Collectors.toMap(DictValue::getValue, DictValue::getLabelDefault));
        return result;
    }
}