package com.matrangola.microcustomer.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LongSummaryStatistics;
import java.util.Map;

@Aspect
@Component
public class ProfileAspect {

    private Map<String, LongSummaryStatistics> methodStats = new HashMap<>();

    @Around("@annotation(Profile)")
    public Object profileExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object retVal = joinPoint.proceed();
        long end = System.currentTimeMillis();
        LongSummaryStatistics stat = methodStats.computeIfAbsent(joinPoint.getSignature().getName(), s -> new LongSummaryStatistics());
        stat.accept(end - begin);
        System.out.printf("\n%s: c:%d avg:%f max:%d min:%d\n", joinPoint.getSignature(), stat.getCount(), stat.getAverage(),
                stat.getMax(), stat.getMin());
        return retVal;
    }
}