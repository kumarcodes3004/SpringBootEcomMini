package com.satyam.SpringEcom.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
//its aspect
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    //*->all mtds ,
    //return type,class name.method-name(args)
//    @Before("execution(* *.*(..)") ->calling for each mtd
    @Before("execution(* com.satyam.SpringEcom.service.ProductService.*(..))")
    public void getMethodCall(){
        LOGGER.info("Method called");
    }

    //@Before -> advice , expression inside before ->PointCut, the sprcifc mtd we are targetting is ->JoinPoint
    //  USE OF JoinPoint , we are targetting specific mtd here
    @Before("execution(* com.satyam.SpringEcom.controller.ProductController.getProducts(..))")
    public void getMethodCall1(JoinPoint jp){
        LOGGER.info("Method called : "+ jp.getSignature().getName());
    }

    // we can club mtd by using  ||
    @Before("execution(* com.satyam.SpringEcom.controller.ProductController.getImageByProductId(..)) || execution(* com.satyam.SpringEcom.controller.ProductController.getProductById(..))")
    public void getMethodCall2(JoinPoint jp){
        LOGGER.info("Get Method called : "+ jp.getSignature().getName());
    }

    @After("execution(* com.satyam.SpringEcom.controller.ProductController.*(..)) || execution(* com.satyam.SpringEcom.service.ProductService.*(..))")
    public void logAfterMtd(JoinPoint jp){
        LOGGER.info("Method executed: "+jp.getSignature().getName());
    }

    //Works if some exception is thrown there is
    //AfterReturning
    //AfterFinally
    @AfterThrowing("execution(* com.satyam.SpringEcom.controller.ProductController.*(..)) || execution(* com.satyam.SpringEcom.service.ProductService.*(..))")
    public void logAfterMtdExcp(JoinPoint jp){
        LOGGER.info("Method throwed exception: "+jp.getSignature().getName());
    }
}
