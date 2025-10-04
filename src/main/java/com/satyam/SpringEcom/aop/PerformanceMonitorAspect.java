package com.satyam.SpringEcom.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceMonitorAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMonitorAspect.class);

    //Around needs several things -> Object need to be returned, there is ProceedingJoinPoint etc.
    @Around("execution (* com.satyam.SpringEcom.service.ProductService.*(..))")
    public Object monitorTime(ProceedingJoinPoint pjp) throws Throwable {
            long start =System.currentTimeMillis();
               Object obj = pjp.proceed();

            long end =System.currentTimeMillis();
            LOGGER.info("Time Taken : "+(end-start) + "ms");
            return obj;
    }
}
