

package com.github.wxiaoqi.security.admin.service;

import com.github.wxiaoqi.merge.facade.IMergeResultParser;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ace
 * @create 2018/2/4.
 */
@Component
public class TableResultParser implements IMergeResultParser {
    @Override
    public List parser(Object o) {
        TableResultResponse response = (TableResultResponse) o;
        TableResultResponse.TableData data = (TableResultResponse.TableData) response.getData();
        List result = data.getRows();
        return result;
    }
}
