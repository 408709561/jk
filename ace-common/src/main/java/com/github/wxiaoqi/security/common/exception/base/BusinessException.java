package com.github.wxiaoqi.security.common.exception.base;

import com.github.wxiaoqi.security.common.constant.RestCodeConstants;
import com.github.wxiaoqi.core.exception.BaseException;

/**
 * 业务异常基础类
 * @author ace
 * @version 2018/1/13.
 */
public class BusinessException extends BaseException {
    public BusinessException(String message) {
        super(message, RestCodeConstants.EX_BUSINESS_BASE_CODE);
    }
}
