package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.AppUser;
import com.github.wxiaoqi.security.app.mapper.AppUserMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.BooleanUtil;
import com.github.wxiaoqi.security.common.util.Sha256PasswordEncoder;
import com.github.wxiaoqi.security.common.validator.ValidatorUtils;
import com.github.wxiaoqi.security.common.validator.group.AddGroup;
import org.springframework.stereotype.Service;

/**
 * @author Mr.AG
 * @version 2018-05-16 20:56:32
 * @email 463540703@qq.com
 */
@Service
public class AppUserBiz extends BusinessBiz<AppUserMapper, AppUser> {
    private Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();

    /**
     * 修改密码
     * @param oldPass
     * @param newPass
     * @return
     */
    public Boolean changePassword(String oldPass, String newPass) {
        AppUser user = this.getUserByMobile(BaseContextHandler.getUsername());
        if (encoder.matches(oldPass, user.getPassword())) {
            String password = encoder.encode(newPass);
            user.setPassword(password);
            this.updateSelectiveById(user);
            return true;
        }
        return false;
    }

    public AppUser getUserByMobile(String mobile) {
        AppUser appUser = new AppUser();
        appUser.setMobile(mobile);
        return this.selectOne(appUser);
    }

    @Override
    public void insertSelective(AppUser entity) {
        ValidatorUtils.validateEntity(entity, AddGroup.class);
        String password = encoder.encode(entity.getPassword());
        entity.setPassword(password);
        entity.setName("xxx_"+entity.getMobile());
        entity.setTenantId(BaseContextHandler.getTenantID());
        entity.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        super.insertSelective(entity);
    }

    @Override
    public void deleteById(Object id) {
        AppUser appUser = this.selectById(id);
        appUser.setIsDeleted(BooleanUtil.switchValue(BooleanUtil.BOOLEAN_FALSE.equals(appUser.getIsDeleted())));
        this.updateSelectiveById(appUser);
    }
}