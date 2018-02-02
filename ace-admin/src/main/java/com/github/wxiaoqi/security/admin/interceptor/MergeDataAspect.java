package com.github.wxiaoqi.security.admin.interceptor;

import com.github.wxiaoqi.security.common.util.MergeUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author ace
 * @create 2018/2/2.
 */
@Aspect
@Component
public class MergeDataAspect {
    public MergeDataAspect(){
        System.out.println(".....");
    }
    @Pointcut("@annotation(com.github.wxiaoqi.security.common.merge.MergeResult)")
    public void methodPointcut() {
    }


    @Around("methodPointcut()")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return MergeUtils.mergeData(pjp);
        }catch(Exception e){
            return pjp.proceed();
        }
    }
}
