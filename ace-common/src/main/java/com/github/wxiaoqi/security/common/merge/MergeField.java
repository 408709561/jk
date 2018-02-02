package com.github.wxiaoqi.security.common.merge;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ace
 * @create 2018/2/1.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE,ElementType.FIELD})
public @interface MergeField {
    String key() default "";
    Class<? extends Object> feign() default Object.class;
    String method() default "";
    boolean isValueNeedMerge() default false;
}
