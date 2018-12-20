/*
 *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>

 *  AG-Enterprise 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  老A将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.github.wxiaoqi.security.common.util;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.audit.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Date;


/**
 * 实体类相关工具类
 * 解决问题： 1、快速对实体的常驻字段，如：crtUser、crtHost、updUser等值快速注入
 *
 * @author Ace
 * @version 2016年4月18日
 * @since 1.7
 */
@Slf4j
public class EntityUtils {

    /**
     * 快速将bean的crtUserName, crtUserId, crtTime、updUserName, updUserId, updTime附上相关值
     *
     * @param entity 实体bean
     * @author 王浩彬
     */
    public static <T> void setCreatAndUpdatInfo(T entity) {
        setCreateInfo(entity);
        setUpdatedInfo(entity);
    }

    /**
     * 快速将bean的crtUserName, crtUserId, crtTime附上相关值
     *
     * @param entity 实体bean
     * @author 王浩彬
     */
    public static <T> void setCreateInfo(T entity) {
        String userName = BaseContextHandler.getName();
        String userId = BaseContextHandler.getUserID();
        String departId = BaseContextHandler.getDepartID();
        String tenantId = BaseContextHandler.getTenantID();
        // 默认属性
        String[] fieldNames = {"crtUserName", "crtUserId", "crtTime","departId","tenantId"};
        if (entity.getClass().getAnnotation(AceAudit.class) != null) {
            Field[] fields = entity.getClass().getDeclaredFields();
            if (fields != null) {
                for (Field field : fields) {
                    if (field.getAnnotation(CrtUserName.class) != null) {
                        fieldNames[0] = field.getName();
                        continue;
                    }
                    if (field.getAnnotation(CrtUserId.class) != null) {
                        fieldNames[1] = field.getName();
                        continue;
                    }
                    if (field.getAnnotation(CrtTime.class) != null) {
                        fieldNames[2] = field.getName();
                        continue;
                    }
                }
            }
        }
        Field field = ReflectionUtils.getAccessibleField(entity, "crtTime");
        // 默认值
        Object[] value = null;
        if (field != null && field.getType().equals(Date.class)) {
            value = new Object[]{userName, userId, new Date(),departId,tenantId};
        }
        // 填充默认属性值
        setDefaultValues(entity, fieldNames, value);
    }

    /**
     * 快速将bean的updUserName, updUserId, updTime附上相关值
     *
     * @param entity 实体bean
     * @author 王浩彬
     */

    public static <T> void setUpdatedInfo(T entity) {
        String userName = BaseContextHandler.getName();
        String userId = BaseContextHandler.getUserID();
        // 默认属性
        String[] fieldNames = {"updUserName", "updUserId", "updTime"};
        if (entity.getClass().getAnnotation(AceAudit.class) != null) {
            Field[] fields = entity.getClass().getDeclaredFields();
            if (fields != null) {
                for (Field field : fields) {
                    if (field.getAnnotation(ModifiedUserName.class) != null) {
                        fieldNames[0] = field.getName();
                        continue;
                    }
                    if (field.getAnnotation(ModifiedUserId.class) != null) {
                        fieldNames[1] = field.getName();
                        continue;
                    }
                    if (field.getAnnotation(ModifiedTime.class) != null) {
                        fieldNames[2] = field.getName();
                        continue;
                    }
                }
            }
        }
        Field field = ReflectionUtils.getAccessibleField(entity, "updTime");
        Object[] value = null;
        if (field != null && field.getType().equals(Date.class)) {
            value = new Object[]{userName, userId, new Date()};
        }
        // 填充默认属性值
        setDefaultValues(entity, fieldNames, value);
    }

    /**
     * 依据对象的属性数组和值数组对对象的属性进行赋值
     *
     * @param entity 对象
     * @param fields 属性数组
     * @param value  值数组
     * @author 王浩彬
     */
    private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            try {
                if (ReflectionUtils.hasField(entity, field)) {
                    ReflectionUtils.invokeSetter(entity, field, value[i]);
                }
            } catch (Exception e) {
                sb.append(field).append(" ");
            }
        }
        if (!sb.toString().isEmpty()) {
            log.error(entity.getClass().getName() + ",部分字段审计失败: " + sb.toString());
        }
    }

    /**
     * 根据主键属性，判断主键是否值为空
     *
     * @param entity
     * @param field
     * @return 主键为空，则返回false；主键有值，返回true
     * @author 王浩彬
     * @version 2016年4月28日
     */
    public static <T> boolean isPKNotNull(T entity, String field) {
        if (!ReflectionUtils.hasField(entity, field)) {
            return false;
        }
        Object value = ReflectionUtils.getFieldValue(entity, field);
        return value != null && !"".equals(value);
    }
}
