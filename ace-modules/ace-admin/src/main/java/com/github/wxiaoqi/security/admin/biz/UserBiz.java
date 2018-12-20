

package com.github.wxiaoqi.security.admin.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.merge.core.MergeCore;
import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.mapper.DepartMapper;
import com.github.wxiaoqi.security.admin.mapper.UserMapper;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @version 2017-06-08 16:23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserBiz extends BaseBiz<UserMapper, User> {
    @Autowired
    private MergeCore mergeCore;

    @Autowired
    private DepartMapper departMapper;

    private Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();


    @Override
    public User selectById(Object id) {
        User user = super.selectById(id);
        try {
            mergeCore.mergeOne(User.class, user);
            return user;
        } catch (Exception e) {
            return super.selectById(id);
        }
    }

    public Boolean changePassword(String oldPass, String newPass) {
        User user = this.getUserByUsername(BaseContextHandler.getUsername());
        if (encoder.matches(oldPass, user.getPassword())) {
            String password = encoder.encode(newPass);
            user.setPassword(password);
            this.updateSelectiveById(user);
            return true;
        }
        return false;
    }

    @Override
    public void insertSelective(User entity) {
        String password = encoder.encode(entity.getPassword());
        String departId = entity.getDepartId();
        EntityUtils.setCreatAndUpdatInfo(entity);
        entity.setPassword(password);
        entity.setDepartId(departId);
        entity.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        entity.setIsDisabled(BooleanUtil.BOOLEAN_FALSE);
        String userId = UUIDUtils.generateUuid();
        entity.setTenantId(BaseContextHandler.getTenantID());
        entity.setId(userId);
        entity.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            entity.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        }
        departMapper.insertDepartUser(UUIDUtils.generateUuid(), entity.getDepartId(), entity.getId(),BaseContextHandler.getTenantID());
        super.insertSelective(entity);
    }

    @Override
    public void updateSelectiveById(User entity) {
        EntityUtils.setUpdatedInfo(entity);
        User user = mapper.selectByPrimaryKey(entity.getId());
        if (!user.getDepartId().equals(entity.getDepartId())) {
            departMapper.deleteDepartUser(user.getDepartId(), entity.getId());
            departMapper.insertDepartUser(UUIDUtils.generateUuid(), entity.getDepartId(), entity.getId(),BaseContextHandler.getTenantID());
        }
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            entity.setTenantId(BaseContextHandler.getTenantID());
        }
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(mapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            entity.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        }
        super.updateSelectiveById(entity);
    }

    @Override
    public void deleteById(Object id) {
        User user = mapper.selectByPrimaryKey(id);
        user.setIsDeleted(BooleanUtil.BOOLEAN_TRUE);
        this.updateSelectiveById(user);
    }

    @Override
    public List<User> selectByExample(Object obj) {
        Example example = (Example) obj;
        example.createCriteria().andEqualTo("isDeleted", BooleanUtil.BOOLEAN_FALSE);
        List<User> users = super.selectByExample(example);
        try {
            mergeCore.mergeResult(User.class, users);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    public User getUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        user.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        user.setIsDisabled(BooleanUtil.BOOLEAN_FALSE);
        return mapper.selectOne(user);
    }

    @Override
    public void query2criteria(Query query, Example example) {
        if (query.entrySet().size() > 0) {
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                Example.Criteria criteria = example.createCriteria();
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
                example.or(criteria);
            }
        }
    }

    public List<String> getUserDataDepartIds(String userId) {
        return mapper.selectUserDataDepartIds(userId);
    }
}
