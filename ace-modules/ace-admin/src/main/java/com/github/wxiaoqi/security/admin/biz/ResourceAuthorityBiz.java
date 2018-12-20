

package com.github.wxiaoqi.security.admin.biz;

import com.github.wxiaoqi.security.admin.entity.ResourceAuthority;
import com.github.wxiaoqi.security.admin.mapper.ResourceAuthorityMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Ace on 2017/6/19.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceAuthorityBiz extends BusinessBiz<ResourceAuthorityMapper,ResourceAuthority> {
    public void deleteByAuthorityIdAndResourceType(String s, String resourceTypeMenu, String type) {
        this.mapper.deleteByAuthorityIdAndResourceType(s,resourceTypeMenu,type);
    }
}
