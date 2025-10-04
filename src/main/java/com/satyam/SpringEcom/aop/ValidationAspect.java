package com.satyam.SpringEcom.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidationAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationAspect.class);


    @Around("execution(* com.satyam.SpringEcom.controller.ProductController.*(..)) && args(postId)")
    public Object ValidateAndUpdateAtController(ProceedingJoinPoint jpj, Long postId) throws Throwable {

        if(postId < 0 ){
            LOGGER.info("Controller :->postId is negative");
            postId = -postId;
            LOGGER.info("new value :"+postId);
        }

        Object obj = jpj.proceed(new Object[]{postId});

        return obj;

    }
    @Around("execution(* com.satyam.SpringEcom.service.ProductService.getProductById(..)) && args(postId)")
    public Object ValidateAndUpdate(ProceedingJoinPoint jpj, Long postId) throws Throwable {

        if(postId < 0 ){
            LOGGER.info("postId is negative");
            postId = -postId;
            LOGGER.info("new value :"+postId);
        }

        Object obj = jpj.proceed(new Object[]{postId});

        return obj;

    }
}
