package com.github.wxiaoqi.security.admin.biz;

import com.github.wxiaoqi.security.admin.entity.Position;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.mapper.PositionMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-02-04 19:06:43
 */
@Service
public class PositionBiz extends BusinessBiz<PositionMapper,Position> {
    /**
     * 更改岗位用户
     * @param positionId
     * @param users
     */
    public void modifyPositionUsers(String positionId, String users) {
        if(StringUtils.isNotBlank(users)){
            mapper.deletePositionUsers(positionId);
            for (String uId : users.split(",")) {
                mapper.insertPositionUser(UUIDUtils.generateUuid(),positionId,uId);
            }
        }
    }

    /**
     * 获取岗位用户
     * @param positionId
     * @return
     */
    public List<User> getPositionUsers(String positionId) {
        return mapper.selectPositionUsers(positionId);
    }
}