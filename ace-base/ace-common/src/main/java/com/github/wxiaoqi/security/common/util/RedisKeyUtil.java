

package com.github.wxiaoqi.security.common.util;

import com.github.ag.core.constants.CommonConstants;
import com.github.wxiaoqi.security.common.constant.RedisKeyConstants;

import java.util.Date;

/**
 * @author ace
 * @create 2018/3/11.
 */
public class RedisKeyUtil {
    /**
     *
     * @param userId
     * @param expire
     * @return
     */
    public static String buildUserAbleKey(String userId,Date expire){
        return CommonConstants.REDIS_USER_TOKEN + RedisKeyConstants.USER_ABLE + userId + ":" + expire.getTime();
    }

    /**
     * 
     * @param userId
     * @param expire
     * @return
     */
    public static String buildUserDisableKey(String userId,Date expire){
        return CommonConstants.REDIS_USER_TOKEN + RedisKeyConstants.USER_DISABLE + userId + ":" + expire.getTime();
    }
}
