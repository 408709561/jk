

package com.github.wxiaoqi.security.admin.biz;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.github.wxiaoqi.security.admin.constant.AdminCommonConstant;
import com.github.wxiaoqi.security.admin.entity.Element;
import com.github.wxiaoqi.security.admin.mapper.ElementMapper;
import com.github.wxiaoqi.security.admin.mapper.UserMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.BooleanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-23 20:27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ElementBiz extends BusinessBiz<ElementMapper,Element> {
    @Autowired
    private UserMapper userMapper;
    /**
     * 获取用户可以访问的资源
     * @param userId
     * @return
     */
    @Cache(key="permission:ele:u{1}")
    public List<Element> getAuthorityElementByUserId(String userId){
        if(BooleanUtil.BOOLEAN_TRUE.equals(userMapper.selectByPrimaryKey(userId).getIsSuperAdmin())){
            return mapper.selectAllElementPermissions();
        }
       return mapper.selectAuthorityElementByUserId(userId,AdminCommonConstant.RESOURCE_TYPE_VIEW);
    }

    public List<Element> getAuthorityElementByUserId(String userId,String menuId){
        return mapper.selectAuthorityMenuElementByUserId(userId,menuId,AdminCommonConstant.RESOURCE_TYPE_VIEW);
    }

    @Cache(key="permission:ele")
    public List<Element> getAllElementPermissions(){
        return mapper.selectAllElementPermissions();
    }

    @Override
    @CacheClear(keys={"permission:ele","permission"})
    public void insertSelective(Element entity) {
        super.insertSelective(entity);
    }

    @Override
    @CacheClear(keys={"permission:ele","permission"})
    public void updateSelectiveById(Element entity) {
        super.updateSelectiveById(entity);
    }

    @MergeResult
    @Override
    public List<Element> selectByExample(Object example) {
        return super.selectByExample(example);
    }
}
