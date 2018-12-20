

package com.github.wxiaoqi.security.common.exception.auth;


import com.github.ag.core.exception.BaseException;
import com.github.wxiaoqi.security.common.constant.RestCodeConstants;

/**
 * Created by ace on 2017/9/8.
 */
public class UserInvalidException extends BaseException {
    public UserInvalidException(String message) {
        super(message, RestCodeConstants.EX_USER_PASS_INVALID_CODE);
    }
}
