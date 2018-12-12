package com.matrangola.microcustomer.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WatchDogAspect {

    @Around(value = "@annotation(wd)")
    public Object profileExecution(ProceedingJoinPoint joinPoint, WatchDog wd) throws Throwable {
        long timeout = wd.timeout();
        long begin = System.currentTimeMillis();
        Object retVal = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - begin;
        if(duration > timeout) {
            System.out.printf("\n%s: duration %d exceeded max %d\n", joinPoint.getSignature(),
                    duration, timeout);
        }
        return retVal;
    }

}
