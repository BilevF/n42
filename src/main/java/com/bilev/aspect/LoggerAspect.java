package com.bilev.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {
    private static final Logger LOG = Logger.getLogger(LoggerAspect.class);


    @Before("execution(* com.bilev..*(..))")
    public void logBeforeWithArgs(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();

        LOG.debug("Call method " + methodName + " with args: ");

        for (Object methodArgs : joinPoint.getArgs())
            if (methodArgs != null)
                LOG.debug(methodArgs.toString());
    }

    @AfterReturning(
            pointcut = "execution(* com.bilev..*(..))",
            returning= "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {

        LOG.debug("Method : " + joinPoint.getSignature().getName());
        if (result != null)
            LOG.debug("Returned value is : " + result.toString());
        else
            LOG.debug("Returned value is : null");
    }

    @After("execution(void com.bilev..*(..))")
    public void logAfter(JoinPoint joinPoint) {

        LOG.debug("Method : " + joinPoint.getSignature().getName() + "completed");
    }

}
